package com.odde.massivemailer.model.onlinetest;

import java.util.List;

public class Answer {

    private Question question;
    private List<String> selectedOptionIds;

    Answer(Question question, List<String> selectedOptionIds) {
        this.question = question;
        this.selectedOptionIds = selectedOptionIds;
    }

    public Question getQuestion() {
        return question;
    }

    public List<String> getSelectedOptionIds() {
        return selectedOptionIds;
    }

    public boolean isCorrect() {
        return getQuestion().verifyAnswer(getSelectedOptionIds());
    }
}
