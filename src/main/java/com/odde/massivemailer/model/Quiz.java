package com.odde.massivemailer.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Quiz {

    private List<Long> questionIds;
    private int numberOfAnsweredQuestions;
    private int NUMBER_OF_QUESTIONS=5;

    public Quiz(){
        questionIds = Question.getNRandomIds(NUMBER_OF_QUESTIONS).collect(Collectors.toList());
        numberOfAnsweredQuestions = 0;
    }

    public Optional<Question> getCurrentQuestion() {
        if (!hasNextQuestion()) {
            return Optional.empty();
        }
        Optional<Question> question = Question.getById(questionIds.get(numberOfAnsweredQuestions));
        numberOfAnsweredQuestions++;

        return question;
    }

    public boolean hasNextQuestion() {
        return questionIds.size()>this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }
}
