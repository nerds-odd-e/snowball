package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

import static com.odde.massivemailer.model.base.Repository.repo;
import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends Entity<Question> {

    @NotNull(message="Description cannot be empty")
    @NotBlank(message="Description cannot be empty")
    private String description;
    private String advice;
    @NotNull(message="Category cannot be empty")
    @NotBlank(message="Category cannot be empty")
    private ObjectId categoryId;
    private boolean isMultiQuestion;
    private boolean isApproved;

    @Override
    public void onBeforeSave() {
    }

    static Question getById(ObjectId questionId) {
        Question question = repo(Question.class).findById(questionId);
        if(question == null) {
           throw new IllegalArgumentException("No question found by given id.");
        }
        return question;
    }

    public Category getCategory() {
        return repo(Category.class).findById(categoryId);
    }

    public String getCategoryName() {
        Category category = getCategory();
        if (category == null) {
            return "";
        }
        return category.getName();
    }

    public Collection<QuestionOption> getOptions() {
        return QuestionOption.getForQuestion(this.getId());
    }

    public boolean verifyAnswer(List<ObjectId> answeredOptionIds) {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        List<ObjectId> collectOptions = optionsByQuestionId.stream().filter(QuestionOption::isCorrect).map(Entity::getId).collect(toList());
        return collectOptions.equals(answeredOptionIds);
    }

    public ArrayList<ObjectId> getCorrectOption() {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        final ArrayList<ObjectId> correctOptions = new ArrayList<>();
        for (QuestionOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getId());
            }
        }
        return correctOptions;
    }

    public boolean isCorrect(String optionId) {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        final ArrayList<String> correctOptions = new ArrayList<>();
        for (QuestionOption option : optionsByQuestionId) {
            if (option.isCorrect()) {
                correctOptions.add(option.getStringId());
            }
        }
        return correctOptions.contains(optionId);
    }

    public void createWrongOption(String optionText) {
        new QuestionOption(optionText, false, getId()).save();
    }

    public void createCorrectOption(String optionText) {
        new QuestionOption(optionText, true, getId()).save();
    }

    boolean belongsTo(Category cat) {
        return cat.getId().equals(categoryId);
    }

}
