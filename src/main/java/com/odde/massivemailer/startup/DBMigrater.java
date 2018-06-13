package com.odde.massivemailer.startup;

import org.javalite.activejdbc.Base;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class DBMigrater {
    public void migrate() {
        for (String migration : migrationFiles()) {
            Arrays.stream(loadMigration(migration).split(";")).filter(s->!s.trim().isEmpty()).forEach(
                    sql-> Base.exec(sql + ";"));
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
