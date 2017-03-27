package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrepareDatabaseStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        getDBReady("jdbc:sqlite:oddemail.db");
        getDBReady("jdbc:sqlite:cucumber_test.db");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public void getDBReady(String dbLink) {
        System.out.println("Preparing database... " + dbLink);
        Base.open("org.sqlite.JDBC", dbLink, "", "");
        new DBMigrater().migrate();
        Base.close();
    }
}
