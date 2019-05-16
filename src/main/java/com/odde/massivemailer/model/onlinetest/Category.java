package com.odde.massivemailer.model.onlinetest;

import java.util.Optional;

public enum Category {
    UNKNOWN(0, ""),
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

    public static Category findByName(String name) {
        return getCategory(name).orElseThrow(()->new RuntimeException(String.format("Category `%s` not found", name)));
    }

    public static Optional<Category> getCategory(String name) {
        for (Category value : Category.values()) {
            if(name.equals(value.name)) return Optional.of(value);
        }
        return Optional.empty();
    }

    public static String getNameById(String id) {
        try {
            return Category.values()[Integer.parseInt(id)].name;
        } catch (NumberFormatException e) {
            return UNKNOWN.name;
        }
    }
}
