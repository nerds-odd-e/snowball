package com.odde.massivemailer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OnlineTest {
    private List<Question> questions;

    public OnlineTest(List<Question> questions) {

        this.questions = questions;
    }

    public static OnlineTest createTestWithQuestions(List<Question> questions, int i) {
        return new OnlineTest(questions);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int countAnsweredQuestions() {
        return (int) questions.stream().filter(t -> t.isAnswered()).count();
    }

    public Question getCurrentQuestion() {
        return questions.get(0);
    }
}
