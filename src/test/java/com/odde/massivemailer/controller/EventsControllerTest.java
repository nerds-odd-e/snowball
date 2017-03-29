package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.TestWithDB;
import com.odde.massivemailer.model.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class EventsControllerTest {
    private EventsController eventsController;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private List<Event> events;
    private Gson json = new Gson();

    @Before
    public void setUpMockService() {
        eventsController = new EventsController();

        events = new ArrayList<>();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void returnEmptyEventsListAsJson() throws Exception {
        eventsController.doGet(request, response);
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    public void returnSingleEventsListAsJson() throws Exception {
        new Event("Test event 1").saveIt();
        eventsController.doGet(request, response);
        Event actualEvents[] = json.fromJson(response.getContentAsString(),Event[].class);
        assertEquals("Test event 1", actualEvents[0].getTitle());
        assertEquals("Singapore", actualEvents[0].getLocation());
    }


    @Test
    public void returnMultipleEventsListAsJson() throws Exception {
        new Event("Test event 1").saveIt();
        new Event("Test event 2").saveIt();
        eventsController.doGet(request, response);

        Event actualEvents[] = json.fromJson(response.getContentAsString(),Event[].class);
        assertEquals("Test event 1", actualEvents[0].getTitle());
        assertEquals("Singapore", actualEvents[0].getLocation());

        assertEquals("Test event 2", actualEvents[1].getTitle());
        assertEquals("Singapore", actualEvents[1].getLocation());
    }

    @Test
    public void shouldAddNewEventWhenTitleIsValid() throws Exception {
        request.setParameter("evtTitle", "title");
        request.setParameter("content", "content");
        request.setParameter("location", "Singapore");
        eventsController.doPost(request, response);
        assertEquals("eventlist.jsp?status=success&msg=Add event successfully", response.getRedirectedUrl());
    }

    @Test
    public void shouldShowErrorWhenTitleIsEmpty() throws Exception {
        request.setParameter("location", "Singapore");
        request.setParameter("evtTitle", "");
        eventsController.doPost(request, response);
        assertEquals("eventlist.jsp?status=failed&msg={ title=<value is missing> }", response.getRedirectedUrl());
    }

    @Test
    public void shouldShowErrorWhenTitleIsNull() throws Exception {
        request.setParameter("location", "Singapore");
        eventsController.doPost(request, response);
        assertEquals("eventlist.jsp?status=failed&msg={ title=<value is missing> }", response.getRedirectedUrl());
    }
}
