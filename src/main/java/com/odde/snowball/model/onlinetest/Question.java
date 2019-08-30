package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.odde.snowball.model.base.Repository.repo;
import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
public class Question extends Entity<Question> {

    @NotNull(message = "Description cannot be empty")
    @NotBlank(message = "Description cannot be empty")
    private String description;
    private String advice;
    @NotNull(message = "Category cannot be empty")
    @NotBlank(message = "Category cannot be empty")
    private ObjectId categoryId;
    private boolean isMultiQuestion;
    private boolean isApproved;
    private boolean isPublic = true;
    private User createUser;

    @Valid
    private List<QuestionOption> options = new ArrayList<>();

    public Question(String description, String advice, ObjectId category, boolean multiple, boolean isApproved) {
        this.description = description;
        this.advice = advice;
        this.categoryId = category;
        this.isMultiQuestion = multiple;
        this.isApproved = isApproved;
    }

    public Question(String description, String advice, ObjectId category, boolean multiple, boolean isApproved, boolean isPublic, User createUser) {
        this(description, advice, category, multiple, isApproved);
        this.isPublic = isPublic;
        this.createUser = createUser;
    }

    public void recordQuestionForUser(User user, LocalDate date) {
        Record record = Record.getOrInitializeRecord(user, this);
        record.update(date);
    }

    public boolean isVisibleForUser(User user) {
        return isPublic || user.equals(createUser);
    }

    public Category category() {
        return repo(Category.class).findById(categoryId);
    }

    public String categoryName() {
        Category category = category();
        if (category == null) {
            return "";
        }
        return category.getName();
    }

    public List<String> correctOptions() {
        return getOptions().stream().filter(QuestionOption::isCorrect).map(QuestionOption::stringId).collect(toList());
    }

    boolean verifyAnswer(List<String> answeredOptionIds) {
        Collection<QuestionOption> optionsByQuestionId = getOptions();
        List<String> collectOptions = optionsByQuestionId.stream().filter(QuestionOption::isCorrect).map(QuestionOption::stringId).collect(toList());
        return collectOptions.equals(answeredOptionIds);
    }

    public Question withOption(String optionDescription, boolean isCorrect) {
        addOption(new QuestionOption(optionDescription, isCorrect));
        return this;
    }

    public QuestionOption addOption(QuestionOption option) {
        options.add(option);
        return option;
    }

    public Question withOption(QuestionOption option) {
        addOption(option);
        return this;
    }
}
