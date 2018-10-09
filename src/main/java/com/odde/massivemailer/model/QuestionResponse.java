package com.odde.massivemailer.model;

public class QuestionResponse extends ApplicationModel{
    private String questionId;
    private String testId;
    private Boolean isAnswerCorrect;

    public QuestionResponse(String testId, String questionId, Boolean isAnswerCorrect) {
        this.questionId = questionId;
        this.testId = testId;
        this.isAnswerCorrect = isAnswerCorrect;
    }


}
