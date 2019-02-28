package com.odde.massivemailer.model.onlinetest;

import java.util.List;

class Answer {

    private Long questionId;
    private List<Integer> selectedOptionIds;

    Answer(Long quesitonId, List<Integer> selectedOptionIds){
        this.questionId = quesitonId;
        this.selectedOptionIds = selectedOptionIds;
    }

}
