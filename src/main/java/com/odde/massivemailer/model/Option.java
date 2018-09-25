package com.odde.massivemailer.model;

public class Option {

    private Long id;
    private String value;
    private boolean answer;

    public Option(Long id, String value, boolean answer) {
        this.id = id;
        this.value = value;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public boolean isAnswer() {
        return answer;
    }
}
