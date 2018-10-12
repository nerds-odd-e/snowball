package com.odde.massivemailer.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Quiz {

    private List<Long> questionIds;
    private int numberOfAnsweredQuestions;
    private int NUMBER_OF_QUESTIONS=5;

    public Quiz(){
        questionIds = Question.getNQuestions(NUMBER_OF_QUESTIONS).collect(Collectors.toList());
        numberOfAnsweredQuestions = 0;
    }

    public void incrementAnsweredQuestions() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("Quiz not started");
        }
        numberOfAnsweredQuestions++;
    }

    public Question getCurrentQuestion() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("Quiz not started");
        }
        return Question.getById(questionIds.get(numberOfAnsweredQuestions)).get();
    }

    public boolean hasNextQuestion() {
        return questionIds.size() > this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }
}
