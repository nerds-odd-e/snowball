package com.odde.massivemailer.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "OnlineTest{" +
                "questions=" + questions +
                '}';
    }

    public List<Question> createUpdatedQuestions(String questionId) {
        return getQuestions().stream()
                .map(question -> {
                    if (question.getLongId().toString().equals(questionId)) {
                        return new Question(question.getDescription(), question.getQuestionOptions(), question.getAdvice(),"Scrum", Long.parseLong(questionId));
                    } else {
                        return question;
                    }
                }).collect(Collectors.toList());
    }
}
