package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

import java.io.Serializable;

public class Event extends Model implements Serializable{
    static {
        validatePresenceOf("title", "location");
    }

    public Event() { }

    public Event(String title) {
        setTitle(title);
        setLocation("Singapore");
    }

    public Event(String title, String content) {
        setTitle(title);
        setContent(content);
        setLocation("Singapore");
    }

    public String getContent() { return getAttribute("description"); }

    public void setContent(String content) {
        if (content == null) {
            content = "";
        }
        set("description", content);
    }

    public String getTitle(){ return getAttribute("title"); }

    public void setTitle(String title) {
        set("title", title);
    }

    public String getLocation(String location) {
        return getAttribute("location");
    }
    public Event setLocation(String location) {
        set("location", location);
        return this;
    }
    public String getAttribute(String name) {
        return (String) get(name);
    }
}