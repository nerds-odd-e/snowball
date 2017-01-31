package com.odde.massivemailer.model;

import java.io.Serializable;
import java.util.Objects;

public class Event implements Serializable {
    private String title;

    private String content;

    public Event(String title) {
        setTitle(title);
    }

    public Event(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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