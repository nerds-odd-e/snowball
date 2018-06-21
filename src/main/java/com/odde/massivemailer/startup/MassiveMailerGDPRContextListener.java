package com.odde.massivemailer.startup;

import com.odde.massivemailer.service.ConsentService;
import com.odde.massivemailer.service.GDPRService;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.TemplateService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MassiveMailerGDPRContextListener  implements ServletContextListener {

    private GDPRService gdprService =
            new GDPRService(
                    MailService.createMailService(),
                    new TemplateService(),
                    new ConsentService()
            );

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("GDPR service started.");
        gdprService.processGDPRConsentEmails();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application is shutting down!");
    }
}
