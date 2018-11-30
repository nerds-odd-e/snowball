package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DBException;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            System.out.print("Migration for the first time...\n");
        }

        new_last_migration_name = resetTable(new_last_migration_name);

        if (new_last_migration_name != null && !new_last_migration_name.equals(last_migration_name)) {
            Base.exec("insert into migration_info (last_migration_name) values (?);", new_last_migration_name);

        }
    }

    private String resetTable(String new_last_migration_name) {
        List<Map> tables = Base.findAll("show tables");
        for (Map table : tables) {
            Object tableName = table.get("Tables_in_massive_mailer_unittest");
            Base.exec("drop table " + tableName);
        }
        for (String migration : migrationFiles()) {
                new_last_migration_name = migration;
                Arrays.stream(loadMigration(migration).split(";")).filter(s -> !s.trim().isEmpty()).forEach(
                        sql -> Base.exec(sql + ";"));
        }
        return new_last_migration_name;
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
        } catch (IOException ignored) {
        }

        return filenames;
    }

    private String loadMigration(String name) {
        String path = "/db/migration/" + name;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)))) {
            String line = reader.readLine();
            List<String> lines = new ArrayList<>();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            return String.join("\n", lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
