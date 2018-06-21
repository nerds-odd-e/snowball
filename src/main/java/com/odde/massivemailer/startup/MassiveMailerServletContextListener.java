package com.odde.massivemailer.startup;

import com.odde.massivemailer.controller.SendMailController;
import com.odde.massivemailer.service.MailService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class MassiveMailerServletContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {}

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
        String emailUserID = System.getenv("MM_EMAIL_USERID");
        String emailPassword = System.getenv(MailService.EMAIL_PASSWORD);

        if(null == emailUserID || null == emailPassword){
            System.err.println("Necessary environment variable does not exist yet!");
        }
    }
}
