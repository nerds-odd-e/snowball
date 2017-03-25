package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("templates")
public class Template extends Model {
    private int Id;
    private String TemplateName;
    private String Subject;
    private String Content;

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        this.TemplateName = templateName;
        set("template_name", templateName);
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        this.Subject = subject;
        set("subject", subject);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
        set("content", content);
    }

}
