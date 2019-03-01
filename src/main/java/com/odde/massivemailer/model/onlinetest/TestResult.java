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

    public Float calculateCorrectRate(String category) {
        if (answers.get(0).isCorrect()) {
            return 1.0f;
        }
        return 0.0f;
    }
}
