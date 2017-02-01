package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Event;

import java.sql.SQLException;

public class SqliteEvent extends SqliteBase {
    public boolean addNewEvent(Event event) {
        int rowAffected = 0;

        try {
            openConnection();

            String sqlStatement =
                    String.format("INSERT INTO Event (Title, Description) VALUES ('%s', '%s')",
                        event.getTitle(),
                        event.getContent());
            rowAffected = statement.executeUpdate(sqlStatement);

            return (rowAffected == 1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection();
        }
    }
}
