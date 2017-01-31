package com.odde.massivemailer.service;


import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;
import static org.junit.Assert.*;

import com.odde.massivemailer.service.impl.EventServiceImpl;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EventServiceTest {

    @Test
    public void returnEventObjectWhenAddEventIsCalled() {
        EventService eventService = new EventServiceImpl();

        assertEquals(1, eventService.addEvent(new Event("Test event 1")));
        assertEquals(2, eventService.addEvent(new Event("Test event 2")));
    }

    @Test
    public void returnErrorWhenEventWithSameTitleAlreadyExists() {
        EventService eventService = new EventServiceImpl();

        assertEquals(1, eventService.addEvent(new Event("Test event 1")));

        try {
            eventService.addEvent(new Event("Test event 1"));
        } catch (Exception e) {
            assertTrue(e instanceof EventAlreadyExistsException);
            assertEquals(e.getMessage(), "Event 'Test event 1' is already exist");
        }
    }

    @Test
    public void returnErrorWhenEventTitleIsEmpty() {
        EventService eventService = new EventServiceImpl();

        try {
            eventService.addEvent(new Event(""));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(e.getMessage(), "Event title is mandatory");
        }


    }

    @Test
    public void returnErrorWhenEventTitleIsNull() {
        EventService eventService = new EventServiceImpl();

        try {
            eventService.addEvent(new Event(null));
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(e.getMessage(), "Event title is mandatory");
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

}
