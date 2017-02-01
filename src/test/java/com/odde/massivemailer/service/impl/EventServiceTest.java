package com.odde.massivemailer.service.impl;


import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.service.EventService;
import static org.junit.Assert.*;

import com.odde.massivemailer.service.impl.EventServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class EventServiceTest {

    Statement statement;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        statement = getStatement();
    }

    @After
    public void tearDown() {
        try {
            statement.executeUpdate("DELETE FROM Event");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void returnEventObjectWhenAddEventIsCalled() {
        EventService eventService = new EventServiceImpl();

        assertEquals(true, eventService.addEvent(new Event("Test event 1")));
        assertEquals(true, eventService.addEvent(new Event("Test event 2")));
    }

//    @Test
//    public void returnErrorWhenEventWithSameTitleAlreadyExists() {
//        EventService eventService = new EventServiceImpl();
//
//        assertEquals(1, eventService.addEvent(new Event("Test event 1")));
//
//        try {
//            eventService.addEvent(new Event("Test event 1"));
//        } catch (Exception e) {
//            assertTrue(e instanceof EventAlreadyExistsException);
//            assertEquals(e.getMessage(), "Event 'Test event 1' is already exist");
//        }
//    }

    @Test
    public void returnErrorWhenEventTitleIsEmpty() {
        EventService eventService = new EventServiceImpl();

        try {
            boolean status = eventService.addEvent(new Event(""));
            assertEquals("true", status, "Event title is mandatory");
        } catch (Exception e) {

        }
    }

    @Test
    public void returnErrorWhenEventTitleIsNull() {
        EventService eventService = new EventServiceImpl();

        try {
            boolean status = eventService.addEvent(new Event(null));
            assertEquals("true", status, "Event title is mandatory");
        } catch (Exception e) {

        }
    }

    @Test
    public void returnEmptyEventListWhenEventsAreNotAvailable() {
        EventService eventService = new EventServiceImpl();

        List<Event> events = eventService.getAll();
        assertTrue(events.isEmpty());
    }

    @Test
    public void returnEventListWhenEventsAreAvailable() {
        EventService eventService = new EventServiceImpl();

        Event e1 = new Event("Test event 1");

        eventService.addEvent(e1);

        List<Event> events = eventService.getAll();

        assertEquals(1, events.size());
        assertEquals(e1, events.get(0));

        Event e2 = new Event("Test event 2");
        eventService.addEvent(e2);

        events = eventService.getAll();

        assertEquals(2, events.size());
        assertEquals(e2, events.get(1));
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
