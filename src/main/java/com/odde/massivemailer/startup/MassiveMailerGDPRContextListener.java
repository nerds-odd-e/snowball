package com.odde.massivemailer.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MassiveMailerGDPRContextListener  implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("GDPR service started.");
        Universe.createUniverse();
        Universe.gdprService().processGDPRConsentEmails();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application is shutting down!");
    }
}
