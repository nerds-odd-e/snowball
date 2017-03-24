package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.NotificationService;
import com.odde.massivemailer.service.impl.*;
import com.odde.massivemailer.service.EventService;
import cucumber.api.java.ca.I;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

/**
 * Created by csd11 on 3/2/17.
 */
@RunWith(TestWithDB.class)
public class SendAllEventsControllerTest {

    private SendAllEventsController sendAllEventsController;

    private EventService eventService;
    private ContactService contactService;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private GMailService gmailService;

    private List<Event> events;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        eventService = new EventServiceImpl();
        contactService = new SqliteContact();
        sendAllEventsController = new SendAllEventsController();
        sendAllEventsController.setGmailService(gmailService);

        events = new ArrayList<>();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void sendNoEventsToNoContactsAsMail() throws Exception {
        contactService.destroyAll();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=N/A&event_in_email=N/A", response.getRedirectedUrl());
    }

    @Test
    public void send1EventToNoContactsAsMail() throws Exception {
        contactService.destroyAll();
        eventService.addEvent(new Event("Testing-1"));
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=N/A&event_in_email=N/A", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo1ContactsAsMail() throws Exception {
        contactService.destroyAll();
        eventService.addEvent(new Event("Testing-1"));
        contactService.addContact(new ContactPerson("testName", "test1@gmail.com", "testLastName"));
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        contactService.destroyAll();
        eventService.addEvent(new Event("Testing-1"));
        contactService.addContact(new ContactPerson("testName1", "test1@gmail.com", "test1LastName"));
        contactService.addContact(new ContactPerson("testName2", "test2@gmail.com", "test2LastName"));
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=2&event_in_email=1", response.getRedirectedUrl());
    }
}
