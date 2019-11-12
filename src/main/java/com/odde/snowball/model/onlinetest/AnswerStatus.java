package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.base.Entity;

public class AnswerStatus extends Entity<AnswerStatus> {
    private String userId;
    private String questionId;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
