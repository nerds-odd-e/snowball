package com.odde.massivemailer.startup;

import com.odde.massivemailer.service.impl.SqliteBase;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDatabaseStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Preparing database...");

        SqliteBase base = new SqliteBase();

        try (Statement statement = base.openConnection()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS mail (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, name VARCHAR(50), email VARCHAR(50) NOT NULL, lastname VARCHAR(50), company VARCHAR(50))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Template (Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, TemplateName VARCHAR(255) NOT NULL, Subject VARCHAR(255), Content NVARCHAR(5000))");
            //statement.executeUpdate("INSERT INTO Template (TemplateName,Subject,Content) VALUES ('Default Template 1', 'Greeting {FirstName}', 'Hi, {FirstName} {LastName} from {Company}')");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            base.closeConnection();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
