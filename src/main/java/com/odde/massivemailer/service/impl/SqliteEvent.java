package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqliteEvent extends SqliteBase {

    public boolean addNewEvent(Event event) {
        int rowAffected = 0;

        try {
            event.saveIt();
            openConnection();

            String sqlStatement =
                    String.format("INSERT INTO Event (Title, Description) VALUES ('%s', '%s')",
                            event.getTitle(),
                            event.getContent());
            System.err.println(sqlStatement);
            rowAffected = statement.executeUpdate(sqlStatement);

            return (rowAffected == 1);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while persisting event");
            e.printStackTrace();

            return false;
        } finally {
            closeConnection();
        }
    }

    public List<Event> getAll() {
        return Event.findAll();
    }

    private List<Event> populateEventList(ResultSet resultSet) throws SQLException {
        List<Event> eventList = new ArrayList<>();

        while (resultSet.next()) {
            String title = resultSet.getString("Title");
            String content = resultSet.getString("Description");

            eventList.add(new Event(title, content));
        }

        return eventList;
    }
}
