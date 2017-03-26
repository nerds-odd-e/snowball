package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.serialiser.ContactPersonSerialiser;
import com.odde.massivemailer.serialiser.EventSerialiser;
import com.odde.massivemailer.serialiser.NotificationSerialiser;
import com.odde.massivemailer.serialiser.TemplateSerialiser;
import com.odde.massivemailer.service.GMailService;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.MockMailService;
import com.odde.massivemailer.service.SMTPConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AppController extends HttpServlet {
    public static final String EMAIL_USERID = "MM_EMAIL_USERID";
    public static final String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";
    private static final String SMTP_ADDR = "smtp.gmail.com";
    private static final int PORT = 587;
    protected MailService gmailService;
    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public void setGmailService(MailService gmailService) {
        this.gmailService = gmailService;
    }

    protected MailService createGmailService() {
        if (null == gmailService) {
            ServletContext application = getServletConfig().getServletContext();
            Object email_sender = application.getAttribute("email_sender");
            if (email_sender != null && ((String)email_sender).equals("mock")) {
                gmailService = new MockMailService();
            }
            else {
                SMTPConfiguration config = new SMTPConfiguration(System.getenv(EMAIL_USERID), System.getenv(EMAIL_PASSWORD), SMTP_ADDR, PORT);
                gmailService = new GMailService(config);
            }
        }

        return gmailService;
    }

    protected Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerialiser())
                .registerTypeAdapter(ContactPerson.class, new ContactPersonSerialiser())
                .registerTypeAdapter(Template.class, new TemplateSerialiser())
                .registerTypeAdapter(Notification.class, new NotificationSerialiser())
                .create();
    }
}
