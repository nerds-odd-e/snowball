package com.odde.massivemailer.model.onlinetest;

import java.util.List;

public class Answer {

    public Long questionId;
    public List<Integer> selectedOptionIds;

    public Answer(Long quesitonId, List<Integer> selectedOptionIds){
        this.questionId = quesitonId;
        this.selectedOptionIds = selectedOptionIds;
    }

}
