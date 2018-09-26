package com.odde.massivemailer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private static List<Question> questions;

    private String description;
    private List<Option> options;
    private String advice;
    private Long answeredOptionId;

    public Question(String description, List<Option> options, String advice) {
        this(description, options, advice, null);
    }

    public Question(String description, List<Option> options, String advice, Long answeredOptionId) {
        this.description = description;
        this.options = options;
        this.advice = advice;
        this.answeredOptionId = answeredOptionId;
    }

    public static List<Question> fetchAll() {
        return questions;
    }

    public static void deleteAll() {
        questions = new ArrayList<>();
    }

    public List<Option> getOptions() {
        return options;
    }

    public String getAdvice() {
        return advice;
    }

    public String getDescription() {
        return description;
    }

    boolean isAnswered() {
        return Objects.nonNull(answeredOptionId);
    }

    public void setAnsweredOptionId(long l) {
        answeredOptionId = l;
    }

    @Override
    public String toString() {
        return "Question{" +
                "description='" + description + '\'' +
                ", options=" + options +
                ", advice='" + advice + '\'' +
                '}';
    }

    public void save() {
        Question.questions.add(this);
    }
}
