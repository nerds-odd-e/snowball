package com.odde.massivemailer.startup;

import com.odde.massivemailer.controller.SendMailController;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MassiveMailerServletContextListener implements ServletContextListener {



    @Override
    public void contextDestroyed(ServletContextEvent arg0) {}

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
        String emailUserID = System.getenv(SendMailController.EMAIL_USERID);
        String emailPassword = System.getenv(SendMailController.EMAIL_PASSWORD);

        if(null == emailUserID || null == emailPassword){
            System.err.println("Necessary environment variable does not exist yet!");
        }
    }
}
