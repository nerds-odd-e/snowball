package com.odde.snowball.model.onlinetest;

import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestResult {
    private List<Answer> answers;

    public TestResult(List<Question> questions, List<Answer> answers) {
        this.answers = answers;

        Map<ObjectId, Question> questionMap = new HashMap<>();
        for (Question q : questions) {
            questionMap.put(q.getId(), q);
        }
    }

    public int correctPercentage() {
        float correct = 0;
        float q_num = 0;
        for (Answer a: answers) {
            q_num++;
            if (a.isCorrect()) {
                correct++;
            }
        }
        return (int) (correct * 100 / q_num);
    }
}
