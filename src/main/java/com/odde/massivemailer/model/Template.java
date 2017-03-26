package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("template")
public class Template extends Model {
    public void setTemplateName(String templateName) {
        set("templateName", templateName);
    }

    public void setSubject(String subject) {
        set("subject", subject);
    }

    public void setContent(String content) {
        set("content", content);
    }

}
