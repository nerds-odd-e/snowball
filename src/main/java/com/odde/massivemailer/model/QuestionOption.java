package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

@Table("question_options")
public class QuestionOption extends ApplicationModel {

    private Long id;
    private long questionId;
    private String body;
    private boolean correct;

    public QuestionOption() {

    }

    public QuestionOption(Long id, long questionId, String body, boolean correct) {
        this.id = id;
        this.questionId = questionId;
        this.body = body;
        this.correct = correct;

        set("id", id);
        set("question_id", questionId);
        set("body", body);
        set("correct", correct);
    }

    public QuestionOption(long id, String body, boolean correct) {
        this.id = id;
        this.body = body;
        this.correct = correct;

        set("id", id);
        set("body", body);
        set("correct", correct);
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public boolean isCorrect() {
        return correct;
    }
}
