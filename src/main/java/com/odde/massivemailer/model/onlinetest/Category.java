package com.odde.massivemailer.model.onlinetest;

public enum Category {
    SCRUM(1, "Scrum"),
    TECH(2, "Tech"),
    TEAM(3, "Team");

    private final int id;
    private final String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
