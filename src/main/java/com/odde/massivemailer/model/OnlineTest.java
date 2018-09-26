package com.odde.massivemailer.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class OnlineTest {
    private List<Question> questions;

    public OnlineTest(List<Question> questions) {

        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int countAnsweredQuestions() {
        return (int) questions.stream().filter(t -> t.isAnswered()).count();
    }

    public Optional<Question> getCurrentQuestion() {
        return questions.stream().filter(o -> !o.isAnswered()).findFirst();
    }

    public boolean isOver() {
        return questions.size() == countAnsweredQuestions();
    }
}
