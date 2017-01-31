package com.odde.massivemailer.model;

import java.io.Serializable;
import java.util.Objects;

public class Event implements Serializable {
    private String title;

    public Event(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return Objects.equals(title, event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
