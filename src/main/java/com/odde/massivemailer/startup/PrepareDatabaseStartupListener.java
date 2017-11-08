package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrepareDatabaseStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        getMySQLDBReady();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public void getMySQLDBReady() {
        System.out.println("Preparing database... ");
        Base.open();
        new DBMigrater().migrate(x -> x.replaceAll("AUTOINCREMENT", "AUTO_INCREMENT"));
        Base.close();
    }
}
