package com.odde.massivemailer.service;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;

import java.util.ArrayList;
import java.util.List;

public class OnlineTestService {

    public OnlineTest generate() {
        List<Question> allQuestions = Question.findAll();
        return generateFromQuestions(allQuestions);
    }

    public OnlineTest generateFromQuestions(List<Question> allQuestions) {
        int size = allQuestions.size();
        if (size > 10) {
            size = 10;
        }
        List<Question> questions = shuffleQuestions(allQuestions, size);
        return new OnlineTest(questions);
    }

    private List<Question> shuffleQuestions(List<Question> allQuestions, int size) {
        List<Question> questions = new ArrayList<>();
        for (int i = size; i > 0; i--) {
            int v = (int) (Math.random() * allQuestions.size());
            questions.add(allQuestions.get(v));
            allQuestions.remove(v);
        }
        return questions;
    }
}
