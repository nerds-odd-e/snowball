package com.odde.massivemailer.controller;

import com.odde.massivemailer.service.GMailService;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.MockMailService;
import com.odde.massivemailer.service.SMTPConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

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
            ms = new GMailService(config);
        }
        return ms;
    }

    private boolean isMailServiceMocked() {
        ServletContext application = getServletConfig().getServletContext();
        Object email_sender = application.getAttribute("email_sender");
        return email_sender != null && ((String) email_sender).equals("mock");
    }

}
