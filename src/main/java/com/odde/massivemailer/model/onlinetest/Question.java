package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.ApplicationModel;
import com.odde.massivemailer.model.callback.QuestionCallbacks;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import java.util.*;
import java.util.stream.Stream;

@Table("questions")
public class Question extends ApplicationModel {

    private static final String DESCRIPTION = "description";
    private static final String ADVICE = "advice";
    private static final String CATEGORY = "category";
    private static final String IS_MULTI_QUESTION = "is_multi_question";

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

    public String getDescription() {
        return getString(DESCRIPTION);
    }

    public String getAdvice() {
        return getString(ADVICE);
    }

    public String getCategory() {
        return getString(CATEGORY);
    }

    public String getCategoryName() {
        return Category.getNameById(getCategory());
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

    public boolean verifyAnswer(String answeredOptionId) {
        Collection<AnswerOption> optionsByQuestionId = getOptions();
        Optional<AnswerOption> correctId = optionsByQuestionId.stream().filter(AnswerOption::isCorrect).findFirst();
        String correctOption = correctId.map(answerOption -> answerOption.getLongId().toString()).orElse(StringUtils.EMPTY);
        return correctOption.equals(answeredOptionId);
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
        return Long.valueOf(options.stream().findFirst().get().getId().toString());
    }

    public Long getCorrectOptionId() {
        Collection<AnswerOption> options = getOptions();
        return Long.valueOf(options.stream().filter(option -> option.isCorrect()).findFirst().get().getId().toString());
    }

    public boolean isMultipleAnswerOptions() {
        return getOptions().stream().filter(AnswerOption::isCorrect).count() > 1;
    }
}
