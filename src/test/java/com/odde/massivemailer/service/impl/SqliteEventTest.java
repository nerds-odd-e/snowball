package com.odde.massivemailer.service.impl;

import org.sqlite.SQLiteConfig;

import com.odde.massivemailer.model.Event;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;
import java.util.Properties;
import static junit.framework.Assert.assertEquals;

public class SqliteEventTest {

	private SqliteEvent sqliteEvent = new SqliteEvent();
    private Statement statement;

    private static String EVENT_NAME = "CSD Training";

	@Before
	public void setUp() throws SQLException, ClassNotFoundException {
         statement = getStatement();
	}

	@After
	public void tearDown() {
        try {
            statement.executeUpdate("DELETE FROM Event WHERE Title = '" + EVENT_NAME + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sqliteEvent.closeConnection();
	}

	@Test
	public void shouldReturnTrueWhenAddNewEventIsSuccessful() throws SQLException {
        Event event = new Event(EVENT_NAME);
        boolean returnedValue = sqliteEvent.addNewEvent(event);

        Assert.assertEquals(true, returnedValue);
    }

    @Test
    public void shouldEventExistsWhenAddNewEventIsSuccessful() throws SQLException {
        Event event = new Event(EVENT_NAME);

        sqliteEvent.addNewEvent(event);

        ResultSet rs = statement.executeQuery(
                "SELECT COUNT(*) FROM Event WHERE Title = '" + EVENT_NAME + "'"
            );

        rs.next();
        int returnedValue = rs.getInt(1);

        Assert.assertEquals(1, returnedValue);
    }


    public Statement getStatement() throws ClassNotFoundException,
            SQLException {

        Class.forName("org.sqlite.JDBC");
        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        Properties properties = sqLiteConfig.toProperties();
        properties.setProperty(SQLiteConfig.Pragma.DATE_STRING_FORMAT.pragmaName, "yyyy-MM-dd HH:mm:ss");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:oddemail.db", properties);

        return connection.createStatement();
    }


}
