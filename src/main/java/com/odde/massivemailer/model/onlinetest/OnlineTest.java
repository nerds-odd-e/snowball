package com.odde.massivemailer.model.onlinetest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class OnlineTest {

    private final List<Long> questionIds;
    private int numberOfAnsweredQuestions;

    public OnlineTest(int questionCount) {
        questionIds = Question.getNRandomIds(questionCount).collect(Collectors.toList());
        numberOfAnsweredQuestions = 0;
    }

    public Question getPreviousQuestion() {
        return Question.getById(questionIds.get(numberOfAnsweredQuestions-1));
    }

    public Question getCurrentQuestion() {
        return Question.getById(questionIds.get(numberOfAnsweredQuestions));
    }

    public Question getNextQuestion() {
        if (!hasNextQuestion()) {
            throw new NoSuchElementException("OnlineTest not started");
        }
        Question question = Question.getById(questionIds.get(numberOfAnsweredQuestions + 1));
        return question;
    }

    public boolean hasNextQuestion() {
        return questionIds.size() > this.getNumberOfAnsweredQuestions();
    }

    public int getNumberOfAnsweredQuestions() {
        return this.numberOfAnsweredQuestions;
    }

    public int getCurrentQuestionIndex() {
        return this.numberOfAnsweredQuestions + 1;
    }

    public int getNumberOfQuestions() {
        return (questionIds!=null) ? questionIds.size() : 0;
    }

    public void moveToNextQuestion() {
        numberOfAnsweredQuestions++;
    }
}
