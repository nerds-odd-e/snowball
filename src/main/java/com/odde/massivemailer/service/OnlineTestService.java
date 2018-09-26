package com.odde.massivemailer.service;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;

import java.util.ArrayList;
import java.util.List;

public class OnlineTestService {

    public OnlineTest generate() {
        List<Question> allQuestions = Question.fetchAll();
        List<Question> questions = new ArrayList<>();
        int size = allQuestions.size();
        if (size > 10) {
            size = 10;
        }
        for (int i = size; i > 0; i--) {
            int v = (int) (Math.random() * allQuestions.size());
            questions.add(allQuestions.get(v));
            allQuestions.remove(v);
        }
        return OnlineTest.createTestWithQuestions(questions, size);
    }
}
