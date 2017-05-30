package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DBMigrater {
    public void migrate() {
        for (String migration : migrationFiles()) {
            System.out.println("---->" + migration);
            Base.exec(loadMigration(migration));
        }
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
