package com.odde.massivemailer.service;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, List<Question>> collect = allQuestions.stream().collect(Collectors.groupingBy(Question::getCategory));

        List<Question> questions = new ArrayList<>();
        collect.forEach((category, questionsByCategory) -> {
            int index1 = (int) (Math.random() * questionsByCategory.size());
            Question question = questionsByCategory.get(index1);
            questions.add(question);
            allQuestions.remove(allQuestions.indexOf(question));
            questionsByCategory.remove(index1);
            if (questionsByCategory.size() > 0) {
                int index = (int) (Math.random() * questionsByCategory.size());
                Question question2 = questionsByCategory.get(index);
                questions.add(question2);
                allQuestions.remove(allQuestions.indexOf(question2));
                questionsByCategory.remove(index);
            }
        });
        for (int i = size - questions.size(); i > 0; i--) {
            int index = (int) (Math.random() * allQuestions.size());
            Question value = allQuestions.get(index);
            questions.add(value);
            allQuestions.remove(value);
        }
        return questions;
    }

}
