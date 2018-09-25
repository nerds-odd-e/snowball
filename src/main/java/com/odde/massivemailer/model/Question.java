package com.odde.massivemailer.model;

import java.util.List;

public class Question {

    private String description;
    private List<Option> options;
    private String advice;

    public Question(String description, List<Option> options, String advice) {
        this.description = description;
        this.options = options;
        this.advice = advice;
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

    public boolean isAnswered() {
        return false;
    }
}
