package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import com.odde.massivemailer.model.base.Repository;
import com.odde.massivemailer.model.callback.QuestionCallbacks;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Table("questions")
public class Question extends ApplicationModel {

    private static final String DESCRIPTION = "description";
    private static final String ADVICE = "advice";
    private static final String CATEGORY = "category";
    private static final String IS_MULTI_QUESTION = "is_multi_question";
    private static final String IS_APPROVED = "is_approved";

    static {
        validatePresenceOf("description");
        callbackWith(new QuestionCallbacks());
    }

    public Question() {
        //required by framework :(

    }

    public Question(String description, String advice, String category, String type) {
        set(DESCRIPTION, description);
        set(ADVICE, advice);
        set(CATEGORY, category);

        if ("multiple".equals(type)) {
            set(IS_MULTI_QUESTION, 1);
            return;
        }
        set(IS_MULTI_QUESTION, 0);

    }

    static Stream<Long> getNRandomIds(int count) {
        LazyList<Model> ids = findBySQL("SELECT id FROM questions ORDER BY RAND() LIMIT ?", count);
        return ids.stream().map(Model::getLongId);
    }

    static Question getById(Long questionId) {
        LazyList<Question> question = where("id = ?", questionId);
        return question.stream().findFirst().orElseThrow(() -> new IllegalArgumentException("No question found by given id."));
    }

    public static List<Question> getNRandom(int count) {
        return findBySQL("SELECT id, description, advice, category, is_multi_question FROM questions ORDER BY RAND() LIMIT ?", count);
    }

    public static List<Question> getNRandomByCategories(Map<String, Integer> categoryMap) {
        List<Question> questions = new ArrayList<>();
        for (String key : categoryMap.keySet()) {
            questions.addAll(Question.getNRandomWhereCategory(categoryMap.get(key), key));
        }

        Collections.shuffle(questions);
        return questions;
    }

    public static List<Question> getNRandomWhereCategory(int count, String category) {
        return findBySQL("SELECT id, description, advice, category, is_multi_question FROM questions WHERE category = ? ORDER BY RAND() LIMIT ?", category, count);
    }

    public static List<Question> getAll() {
        return findBySQL("SELECT id, description, advice, category, is_multi_question FROM questions ORDER BY id");
    }

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public String getIsApproved() {
        return getString(IS_APPROVED);
    }

    public String getAdvice() {
        return getString(ADVICE);
    }

    public Category getCategory() {
        if (getString(CATEGORY).isEmpty()) {
            return null;
        }
        return Category.repository().findById(getString(CATEGORY));
    }

    public String getCategoryName() {
        Category category = getCategory();
        if (category == null) {
            return "";
        }
        return category.getName();
    }

    public boolean getIsMultiQuestion() {
        Integer type = getInteger(IS_MULTI_QUESTION);
        if (type == null) {
            return false;
        }
        return getInteger(IS_MULTI_QUESTION) == 1;
    }

    public Collection<AnswerOption> getOptions() {

        return AnswerOption.getForQuestion(this.getLongId());
    }

    public boolean verifyAnswer(List<String> answeredOptionIds) {
        Collection<AnswerOption> optionsByQuestionId = getOptions();
        List<String> collectOptions = optionsByQuestionId.stream().filter(AnswerOption::isCorrect).map(answerOption -> answerOption.getLongId().toString()).collect(toList());
        return collectOptions.equals(answeredOptionIds);
    }

    public ArrayList<Long> getCorrectOption() {
        Collection<AnswerOption> optionsByQuestionId = getOptions();
        final ArrayList<Long> correctOptions = new ArrayList<>();
        for (AnswerOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getLongId());
            }
        }
        return correctOptions;
    }

    public boolean isCorrect(Long optionId) {
        Collection<AnswerOption> optionsByQuestionId = getOptions();
        final ArrayList<Long> correctOptions = new ArrayList<>();
        for (AnswerOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getLongId());
            }
        }
        return correctOptions.contains(optionId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        if (!super.equals(o)) return false;
        Question question = (Question) o;
        return Objects.equals(getDescription(), question.getDescription()) && Objects.equals(getAdvice(), question.getAdvice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getAdvice());
    }

    public void createWrongOption(String optionText) {
        AnswerOption.createIt("description", optionText, "question_id", getLongId(), "is_correct", 0);
    }

    public void createCorrectOption(String optionText) {
        AnswerOption.createIt("description", optionText, "question_id", getLongId(), "is_correct", 1);
    }

    public Long getFirstOptionId() {
        Collection<AnswerOption> options = getOptions();
        return Long.valueOf(options.stream().findFirst().get().getStringId());
    }

    public boolean isMultipleAnswerOptions() {
        return getOptions().stream().filter(AnswerOption::isCorrect).count() > 1;
    }

    boolean belongsTo(Category cat) {
        return cat.getStringId().equals(get(CATEGORY));
    }

    boolean isPublic() {
        return true;
    }

}
