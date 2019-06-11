package com.odde.snowball.startup;

import com.odde.snowball.service.MailService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@SuppressWarnings("unused")
@WebListener()
public class SnowballServletContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {}

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        String emailUserID = System.getenv("MM_EMAIL_USERID");
        String emailPassword = System.getenv(MailService.EMAIL_PASSWORD);

        if(null == emailUserID || null == emailPassword){
            System.err.println("Necessary environment variable does not exist yet!");
        }
    }
}
