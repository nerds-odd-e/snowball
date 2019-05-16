package com.odde.massivemailer.model.onlinetest;


public class QuestionCategory {
    private final int id;
    private final String name;

    QuestionCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static QuestionCategory createQuestionCategory() {
        return new QuestionCategory(1, "Scrum");
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
