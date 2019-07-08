package com.odde.snowball.model.onlinetest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class QuestionOption {

    private ObjectId optionId;
    @Size(min=1, message="`description` cannot be empty")
    private String description;
    private boolean isCorrect;

    public QuestionOption(String optionDescription, boolean isCorrect) {
        this.description = optionDescription;
        this.isCorrect = isCorrect;
        this.optionId = new ObjectId();
    }

    public String stringId() {
        return optionId.toString();
    }
}
