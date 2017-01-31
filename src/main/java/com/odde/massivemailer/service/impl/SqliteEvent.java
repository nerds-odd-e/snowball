package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Event;

import java.sql.SQLException;

/**
 * Created by csd11 on 31/1/17.
 */
public class SqliteEvent extends SqliteBase {
    public int addNewEvent(com.odde.massivemailer.model.Event event) {
        int rowAffected = 0;

        try {
            openConnection();
            String sqlStatement =
                    String.format("INSERT INTO Event (Title, Description) VALUES ('%s', '%s')",
                        event.getTitle(),
                        event.getContent());
            rowAffected = statement.executeUpdate(sqlStatement);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return rowAffected;
    }

/*
    private int saveContactToDatabase(String name, String email, String lastname, String company)
            throws SQLException {
        int rowAffected = 0;
        if (!contactExisted(email))
            rowAffected = statement
                    .executeUpdate("INSERT INTO mail(name,email,lastname,company) VALUES ('"
                            + name + "', '" + email + "','" + lastname + "','" + company + "')");
        return rowAffected;
    }
    */
}
