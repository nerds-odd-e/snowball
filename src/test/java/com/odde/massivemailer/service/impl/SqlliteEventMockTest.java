package com.odde.massivemailer.service.impl;

import com.odde.massivemailer.model.Event;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SqlliteEventMockTest {
    private SqliteEvent sqliteEvent;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private Statement mockStatement;

    @Mock
    private Connection mockConnection;

    private static String EVENT_NAME = "CSD Training";

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        sqliteEvent = new SqliteEvent();
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        sqliteEvent.setConnection(mockConnection);
    }

    @Test
    public void shouldReturnFalseWhenAddNewEventThrowsDatabaseFailure() throws SQLException {
        String sql = "INSERT INTO Event (Title, Description) VALUES ('CSD Training', 'null')";

        when(mockStatement.executeUpdate(sql)).thenThrow(new SQLException());
        Assert.assertFalse(sqliteEvent.addNewEvent(new Event(EVENT_NAME)));
    }

    @Test
    public void shouldReturnEmptyEventListWhenDatabaseFailure() throws SQLException {
        String sql = "SELECT * FROM Event";

        when(mockStatement.executeQuery(sql)).thenThrow(new SQLException());

        List<Event> events = sqliteEvent.getAll();

        assertTrue(events.isEmpty());
    }
}
