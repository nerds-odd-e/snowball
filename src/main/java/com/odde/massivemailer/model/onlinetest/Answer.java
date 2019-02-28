package com.odde.massivemailer.model.onlinetest;

import java.util.List;

public class Answer {

    private Long questionId;
    private List<String> selectedOptionIds;

    Answer(Long quesitonId, List<String> selectedOptionIds) {
        this.questionId = quesitonId;
        this.selectedOptionIds = selectedOptionIds;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public List<String> getSelectedOptionIds() {
        return selectedOptionIds;
    }
}
