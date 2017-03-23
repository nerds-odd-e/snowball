package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Event extends Model {
    private String title;

    private String content;

    private Map<String, String> attributes = new HashMap<>();
    private static final String TITLE = "Title";
    private static final String CONTENT = "Content";

    public Event(String title) {
        setTitle(title);
    }

    public Event(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public String getContent() { return getAttribute(CONTENT); }

    public void setContent(String content) {
        if (content == null) {
            content = "";
        }
        setAttribute(CONTENT, content);
    }

    public String getTitle(){ return getAttribute(TITLE); }

    public void setTitle(String title) { setAttribute(TITLE, title); }

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

    private void setAttribute(String name, String value)
    {
        attributes.put(name, value);
    }

    public String getAttribute(String name) { return attributes.containsKey(name) ? attributes.get(name) : ""; }

    public Set<String> getAttributeKeys()
    {
        return attributes.keySet();
    }
}