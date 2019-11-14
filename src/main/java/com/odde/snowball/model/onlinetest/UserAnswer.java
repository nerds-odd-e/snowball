package com.odde.snowball.model.onlinetest;

import java.util.List;

public class UserAnswer {

    private Question question;
    private List<String> selectedOptionIds;

    UserAnswer(Question question, List<String> selectedOptionIds) {
        this.question = question;
        this.selectedOptionIds = selectedOptionIds;
    }

    public Question getQuestion() {
        return question;
    }

    private List<String> getSelectedOptionIds() {
        return selectedOptionIds;
    }

    public boolean isCorrect() {
        return getQuestion().verifyAnswer(getSelectedOptionIds());
    }
}
