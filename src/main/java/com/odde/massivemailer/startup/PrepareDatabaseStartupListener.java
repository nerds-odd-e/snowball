package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrepareDatabaseStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        getMySQLDBReady("cucumber_test");
        getMySQLDBReady("oddemail");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public void getMySQLDBReady(String dbLink) {
        System.out.println("Preparing database... " + dbLink);
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/" + dbLink, "root", "");
        new DBMigrater().migrate(x -> x.replaceAll("AUTOINCREMENT", "AUTO_INCREMENT"));
        Base.close();
    }
}
