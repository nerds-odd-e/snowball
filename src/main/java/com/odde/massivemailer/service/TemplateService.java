package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import org.javalite.activejdbc.Base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;


/**
 * Created by csd on 30/5/17.
 */
public class TemplateService {

    public String getDefaultTemplate() throws Exception
    {
        String template = null;
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:oddemail.db", "", "");

        Connection conn = Base.connection();
        List<Map> rowProcessor =  Base.findAll("Select content from Template");

        template = (String) rowProcessor.get(0).get("Content");

        Base.close();
        return template;
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
