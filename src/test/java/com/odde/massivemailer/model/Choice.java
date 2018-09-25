package com.odde.massivemailer.model;

public class Choice {
    public final long id;
    public final String value;
    private final boolean answerFlg;

    public Choice(long id, String value, boolean answerFlg) {
        this.id = id;
        this.value = value;
        this.answerFlg = answerFlg;
    }

    public boolean isAnswerFlg() {
        return answerFlg;
    }


}
