package com.odde.massivemailer.model.onlinetest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestResult {
    private Map<Long, Question> questionMap;
    private List<Answer> answers;

    public TestResult(List<Question> questions, List<Answer> answers) {
        this.answers = answers;

        questionMap = new HashMap<>();
        for (Question q : questions) {
            questionMap.put(q.getLongId(), q);
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
