package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@SuppressWarnings("unused")
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
        Base.open();
        new DBMigrater().migrate();
        Base.close();
    }
}
