package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Template;
import org.javalite.activejdbc.Base;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by csd on 30/5/17.
 */
public class TemplateService {

    public Template getDefaultTemplate() throws Exception
    {
        Template defaultTemplate = null;
        List<Template> templateList = new ArrayList<Template>();
        Template template = null;
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:oddemail.db", "", "");

        Connection conn = Base.connection();
        List<Map> rowProcessor =  Base.findAll("Select Id, TemplateName, Subject, Content from Template");

        for (Map rowTemplate : rowProcessor) {
            template = new Template();
            template.setId(rowTemplate.get("Id"));
            template.setTemplateName((String)rowTemplate.get("TemplateName"));
            template.setSubject((String) rowTemplate.get("Subject"));
            template.setContent((String) rowTemplate.get("Content"));

            //
            templateList.add(template);
        }

        Base.close();

        if (templateList.size() >= 1) {
            defaultTemplate = templateList.get(0);
        }

        return defaultTemplate;
    }


    public List<String> populatePreviewEmailTemplate(String template, Event course) {
        return null;
    }

    public String populateTemplate(String template, Event course) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        TemplateService serv = new TemplateService();
        serv.getDefaultTemplate();
    }

}
