package com.odde.massivemailer.model.onlinetest;

import com.odde.massivemailer.model.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.odde.massivemailer.model.base.Repository.repo;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionOption extends Entity<QuestionOption> {

    @Size(min=1, message="`description` cannot be empty")
    private String description;
    private boolean isCorrect;
    @NotNull(message="`description` cannot be empty")
    @NotBlank(message="`questionId` cannot be empty")
    private ObjectId questionId;

    @Override
    public void onBeforeSave() {
    }

    public QuestionOption(String description, boolean isCorrect) {
        setDescription(description);
        setCorrect(isCorrect);
    }

    static Collection<QuestionOption> getForQuestion(ObjectId questionId) {
        return repo(QuestionOption.class).findBy("questionId", questionId);
    }

    static Optional<QuestionOption> getById(String optionId) {
        return Optional.ofNullable(repo(QuestionOption.class).findByStringId(optionId));
    }

    public static QuestionOption createIt(ObjectId questionId, String description, boolean isCorrect) {
        return new QuestionOption(description, isCorrect, questionId).save();
    }

    void addToQuestion(ObjectId questionId) {
        setQuestionId(questionId);
        save();
    }

}
