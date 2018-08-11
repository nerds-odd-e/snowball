package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class DBMigrater {
    public void migrate() {
        String last_migration_name = "";
        String new_last_migration_name = null;
        try {
            last_migration_name = (String) Base.firstCell("select max(last_migration_name) from migration_info;");
            if (last_migration_name == null) {
                last_migration_name = "";
            }
        } catch (DBException e) {
            System.out.printf("Migration for the first time...\n");
        }

        for (String migration : migrationFiles()) {
            if (migration.compareTo(last_migration_name)> 0) {
                new_last_migration_name = migration;
                Arrays.stream(loadMigration(migration).split(";")).filter(s -> !s.trim().isEmpty()).forEach(
                        sql -> Base.exec(sql + ";"));
            }
        }

        if (new_last_migration_name != null && !new_last_migration_name.equals(last_migration_name)) {
            Base.exec("insert into migration_info (last_migration_name) values (?);", new_last_migration_name);

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
            String line = reader.readLine();
            List<String> lines = new ArrayList<String>();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            return String.join("\n", lines);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
