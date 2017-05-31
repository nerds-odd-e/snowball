package com.odde.massivemailer.service;

import com.odde.massivemailer.model.*;
import org.javalite.activejdbc.Base;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by csd on 30/5/17.
 */
@Deprecated
public class TemplateService {

    public Template getDefaultTemplate(String templateName) throws Exception
    {
        Template defaultTemplate = null;
        List<Template> templateList = new ArrayList<Template>();
        Template template = null;
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:oddemail.db", "", "");

        Connection conn = Base.connection();
        List<Map> rowProcessor =  Base.findAll("Select Id, TemplateName, Subject, Content from Template where templateName='Default Template 1'");

        if (rowProcessor == null || rowProcessor.size()<1) return template;

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

        //templateList =  Template.findByTemplateName(templateName);


        if (templateList.size() >= 1) {
            Template templateDB = templateList.get(0);
            defaultTemplate = new Template((String) templateDB.get("templateName"), (String) templateDB.get("subject"), (String) templateDB.get("content"));

            defaultTemplate.setId((String) templateDB.getId());
            System.out.println(defaultTemplate);
        }

        return defaultTemplate;
    }

    public Template getTemplateByName(String templateName) throws Exception{
        return null;
    }

    public Mail getPreviewEmail(Template template, Course course)
    {
        return null;
    }

    static public List<Mail> populateTemplate(Template template, Course course, List<ContactPerson> courseParticipants)
    {
        List<Mail> mails = new ArrayList<Mail>();

        String content;
        String contactEmailId;

        for (ContactPerson contactPerson : courseParticipants) {
            content = getEmailContentFromTemplate(template, contactPerson, course);
            contactEmailId = contactPerson.getEmail();

            //
            Mail mail = Mail.createEventMail(content, contactEmailId);
            //
            mails.add(mail);
        }

        return mails;
    }

    private static String getEmailContentFromTemplate(Template template, ContactPerson contactPerson, Course course) {

        return null;
    }



    public static void main(String[] args) throws Exception {
        TemplateService serv = new TemplateService();
        //serv.getDefaultTemplate();
    }

}
