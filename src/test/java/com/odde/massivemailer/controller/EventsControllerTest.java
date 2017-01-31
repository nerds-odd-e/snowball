package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.exception.EventAlreadyExistsException;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.service.EventService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

public class EventsControllerTest {
    private EventsController eventsController;

    private EventService eventService;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private List<Event> events;

    @Before
    public void setUpMockService() {
        eventService = Mockito.mock(EventService.class);

        eventsController = new EventsController(eventService);

        events = new ArrayList<>();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnEmptyEventsListAsJson() throws Exception {
        Mockito.when(eventService.getAll()).thenReturn(events);

        eventsController.doGet(request, response);
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    public void returnSingleEventsListAsJson() throws Exception {
        events.add(new Event("Test event 1"));

        Mockito.when(eventService.getAll()).thenReturn(events);

        eventsController.doGet(request, response);
        assertEquals("[{\"title\":\"Test event 1\"}]", response.getContentAsString());
    }


    @Test
    public void returnMultipleEventsListAsJson() throws Exception {
        events.add(new Event("Test event 1"));
        events.add(new Event("Test event 2"));

        Mockito.when(eventService.getAll()).thenReturn(events);

        eventsController.doGet(request, response);
        assertEquals("[{\"title\":\"Test event 1\"},{\"title\":\"Test event 2\"}]", response.getContentAsString());
    }

    @Test
    public void addNewEvent() throws Exception {
        Event e = new Event("Test event 1");
        events.add(e);

        Mockito.when(eventService.addEvent(e)).thenReturn(1);

        request.setParameter("title", "Test event 1");
        request.setParameter("content", "Test content 1");

        eventsController.doPost(request, response);
        assertEquals("eventlist.jsp?status=success&msg=Add event successfully", response.getRedirectedUrl());
    }

    @Test
    public void addExistingEvent() throws Exception {
        Event e = new Event("Test event 1");
        events.add(e);

        Mockito.when(eventService.addEvent(e)).thenThrow(
                new EventAlreadyExistsException("Event 'Test event 1' is already exist"));

        request.setParameter("title", "Test event 1");
        request.setParameter("content", "Test content 1");

        eventsController.doPost(request, response);
        assertEquals("eventlist.jsp?status=failed&msg=Event 'Test event 1' is already exist", response.getRedirectedUrl());
    }
}
