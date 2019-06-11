package com.odde.snowball.model.onlinetest;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class Answer {

    private Question question;
    private List<ObjectId> selectedOptionIds;

    Answer(Question question, List<String> selectedOptionIds) {
        this.question = question;
        this.selectedOptionIds = selectedOptionIds.stream().map(ObjectId::new).collect(Collectors.toList());
    }

    public Question getQuestion() {
        return question;
    }

    public List<ObjectId> getSelectedOptionIds() {
        return selectedOptionIds;
    }

    public boolean isCorrect() {
        return getQuestion().verifyAnswer(getSelectedOptionIds());
    }
}
