package com.odde.massivemailer.startup;

import com.odde.massivemailer.service.impl.SqliteBase;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class PrepareDatabaseStartupListener implements ServletContextListener {
    private static final String[] MIGRATION_FILES = {
            "20150806140746_Add_firstname_lastname_to_mail_table.sql",
            "20150906140746_Add_template_table.sql",
            "20161115132000_create_notifications.sql",
            "20161115151100_create_unique_index_template_templatename.sql",
            "20161115151800_insert_default_template.sql",
            "20161115153900_alter_notifications_and_notification_details.sql",
            "20161115175300_add_notification_id_to_notifications.sql",
            "20170130141000_create_event_table.sql",
            "20170323170300_create_events_table.sql",
    };

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        getDBReady("jdbc:sqlite:oddemail.db");
        getDBReady("jdbc:sqlite:testdb.db");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void getDBReady(String dbLink) {
        System.out.println("Preparing database... " + dbLink);
        SqliteBase base = new SqliteBase(dbLink);
        try (Statement statement = base.openConnection()) {
            for (String migration : MIGRATION_FILES) {
                statement.executeUpdate(loadMigration(migration));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            base.closeConnection();
        }
    }

    private String loadMigration(String name) {
        String path = "/db/migration/" + name;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)))) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();

            while (line != null) {
                builder.append(line);

                line = reader.readLine();
            }

            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
