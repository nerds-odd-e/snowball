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
        if (mailService == null) {
            mailService = MailService.createMailService();
        }
        return mailService;
    }

    protected HashMap getParameterFromRequest(HttpServletRequest req, String[] reqFields) {
        HashMap map = new HashMap();

        for(String field: reqFields)
            map.put( field, req.getParameter(field));
        return map;
    }
}
