package com.odde.massivemailer.model.onlinetest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Quiz {

    private final List<Long> questionIds;
    private int numberOfAnsweredQuestions;

    public Quiz(int questionCount) {
        questionIds = Question.getNRandomIds(questionCount).collect(Collectors.toList());
        numberOfAnsweredQuestions = 0;
    }

    public Question getCurrentQuestion() {
        return Question.getById(questionIds.get(numberOfAnsweredQuestions-1));
    }

    public Question getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("Quiz not started");
        }
        Question question = Question.getById(questionIds.get(numberOfAnsweredQuestions));
        numberOfAnsweredQuestions++;
        return question;
    }

    public boolean hasNextQuestion() {
        return questionIds.size()>this.getNumberOfAnsweredQuestions();
    }

    private int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }
}
