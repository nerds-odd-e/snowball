package com.odde.massivemailer.model;

/**
 * Created by csd on 2017/01/31.
 */
public class Event {

    private String title;

    private String content;

    public Event(String title) {
        setTitle(title);
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
    public String toString() {
        return "Event{" +
                "title=" + title +
                ", content=" + content +
                '}';
    }
}
