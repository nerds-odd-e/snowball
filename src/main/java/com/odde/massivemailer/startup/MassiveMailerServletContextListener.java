package com.odde.massivemailer.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MassiveMailerServletContextListener implements ServletContextListener {

    private static final String EMAIL_USERID = "MM_EMAIL_USERID";
    private static final String EMAIL_PASSWORD = "MM_EMAIL_PASSWORD";

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {}

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
        System.out.println("Printing existing environment variables");

        String emailUserID = System.getenv(EMAIL_USERID);
        String emailPassword = System.getenv(EMAIL_PASSWORD);

        if(null == emailUserID || null == emailPassword){
            System.err.println("Necessary environment variable does not exist yet!");
        }
    }
}
