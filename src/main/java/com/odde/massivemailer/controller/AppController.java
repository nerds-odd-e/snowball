package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.serialiser.ContactPersonSerialiser;
import com.odde.massivemailer.serialiser.EventSerialiser;
import com.odde.massivemailer.service.impl.GMailService;
import com.odde.massivemailer.service.impl.SMTPConfiguration;
import org.javalite.activejdbc.Base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppController extends HttpServlet {
    public static final String EMAIL_USERID = "MM_EMAIL_USERID";
    public static final String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";
    private static final String SMTP_ADDR = "smtp.gmail.com";
    private static final int PORT = 587;
    protected GMailService gmailService;
    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public void setGmailService(GMailService gmailService) {
        this.gmailService = gmailService;
    }

    protected GMailService createGmailService() {
        if (null == gmailService) {
            SMTPConfiguration config = new SMTPConfiguration(System.getenv(EMAIL_USERID), System.getenv(EMAIL_PASSWORD), SMTP_ADDR, PORT);
            gmailService = new GMailService(config);
        }
        return gmailService;
    }

    protected Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerialiser())
                .registerTypeAdapter(ContactPerson.class, new ContactPersonSerialiser())
                .create();
    }
}
