package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Event;

import java.sql.SQLException;
import java.util.*;

public class SqliteEvent extends SqliteBase {
    private Map<Integer, Event> events = new LinkedHashMap<>();

    public boolean addNewEvent(Event event) {
        int rowAffected = 0;

        try {
            openConnection();

            String sqlStatement =
                    String.format("INSERT INTO Event (Title, Description) VALUES ('%s', '%s')",
                        event.getTitle(),
                        event.getContent());
            rowAffected = statement.executeUpdate(sqlStatement);

            if (rowAffected == 1) {
                events.put(events.size() + 1, event);
            }

            return (rowAffected == 1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }

    public List<Event> getAll() {
        return new ArrayList<>(events.values());
    }
}
