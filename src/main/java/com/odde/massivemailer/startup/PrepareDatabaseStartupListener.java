package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebListener()
public class PrepareDatabaseStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        getDBReady("jdbc:sqlite:oddemail.db");
        getDBReady("jdbc:sqlite:testdb.db");
        getDBReady("jdbc:sqlite:cucumber_test.db");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void getDBReady(String dbLink) {
        System.out.println("Preparing database... " + dbLink);
        Base.open("org.sqlite.JDBC", dbLink, "", "");
        for (String migration : migrationFiles()) {
            Base.exec(loadMigration(migration));
        }
        Base.close();
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

    private List<String> migrationFiles() {
        return getResourceFiles("/db/migration");
    }

    private List<String> getResourceFiles(String path) {
        List<String> filenames = new ArrayList<>();

        try (
                InputStream in = getClass().getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        } catch (IOException e) {
        }

        return filenames;
    }
}
