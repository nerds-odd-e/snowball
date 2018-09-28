package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Table("questions")
public class Question extends ApplicationModel {

    private Long answeredOptionId;

    public Question() {
    }

    public Question(String description, List<QuestionOption> questionOptions, String advice) {
        this(description, questionOptions, advice, null);
    }

    public Question(String description, List<QuestionOption> questionOptions, String advice, Long answeredOptionId) {
        this.answeredOptionId = answeredOptionId;
        set("body", description);
        set("advice", advice);
    }

    public List<QuestionOption> getQuestionOptions() {
        return QuestionOption.find("question_id = " + getId());
    }

    public String getAdvice() {
        return getString("advice");
    }

    public String getDescription() {
        return getString("body");
    }

    boolean isAnswered() {
        return Objects.nonNull(answeredOptionId);
    }

    public void setAnsweredOptionId(long l) {
        answeredOptionId = l;
    }

    @Override
    public String toString() {
        return "Question{" +
                "Id=" + getId() +
                "description=" + getDescription() +
                "advice=" + getAdvice() +
                "answeredOptionId=" + answeredOptionId +
                '}';
    }
}
