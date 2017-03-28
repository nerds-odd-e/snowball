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

    public Event(String title, String content, String location) {
        setTitle(title);
        setContent(content);
        setLocation(location);
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

    public String getLocation() {
        return getAttribute("location");
    }
    public String getAttribute(String name) {
        return (String) get(name);
    }

    public void setLocation(String location) {
        set("location", location);
    }

    public String getLocation(){
        return getAttribute("location");
    }


}