package com.odde.massivemailer.controller;

import com.odde.massivemailer.service.GMailService;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.MockMailService;
import com.odde.massivemailer.service.SMTPConfiguration;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Properties;

public class AppController extends HttpServlet {
    public static final String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";
    private static final String SMTP_ADDR = "smtp.gmail.com";
    private static final int PORT = 587;

    protected MailService mailService;

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    protected MailService getMailService() {
        if (null == mailService) {
            mailService = createMailService();
        }
        return mailService;
    }

    private MailService createMailService() {
        MailService ms = new MockMailService();
        if (! isMailServiceMocked()) {
            SMTPConfiguration config = new SMTPConfiguration(System.getenv("MM_EMAIL_USERID"), System.getenv(EMAIL_PASSWORD), SMTP_ADDR, PORT);
            final Session mailSession = createMailSession();
            ms = new GMailService(config, mailSession);
        }
        return ms;
    }

    public Session createMailSession() {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        return Session.getDefaultInstance(props);
    }

    private boolean isMailServiceMocked() {
        return "test".equals(System.getProperty("active_env"));
    }

    protected HashMap getParameterFromRequest(HttpServletRequest req, String[] reqFields) {
        HashMap map = new HashMap();

        for(String field: reqFields)
            map.put( field, req.getParameter(field));
        return map;
    }
}
