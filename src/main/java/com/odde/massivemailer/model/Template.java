package com.odde.massivemailer.model;

/**
 * Created by Cadet on 11/26/2015.
 */
public class Template {
    private int Id;
    private String TemplateName;
    private String Subject;
    private String Content;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        this.TemplateName = templateName;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        this.Subject = subject;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

}
