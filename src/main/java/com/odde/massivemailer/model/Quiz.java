package com.odde.massivemailer.model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Quiz {

    private List<Long> questionIds;
    private int numberOfAnsweredQuestions;
    private int number_of_questions =5;

    public Quiz(int questionCount) {
        number_of_questions = questionCount;
        questionIds = Question.getNRandomIds(number_of_questions).collect(Collectors.toList());
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

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }
}
