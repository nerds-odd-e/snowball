package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            rowAffected = getStatement().executeUpdate(sqlStatement);

            if (rowAffected == 1) {
                events.put(events.size() + 1, event);
            }

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
        List<Event> eventList = Collections.emptyList();

        ResultSet resultSet = null;
        try {
            openConnection();

            String sqlStatement = "SELECT * FROM Event";

            Statement statement = getStatement();
            resultSet = statement.executeQuery(sqlStatement);

            eventList = populateEventList(resultSet);

            resultSet.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error while listing event");
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return eventList;
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
