package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.service.ContactService;
import com.odde.massivemailer.service.impl.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

/**
 * Created by csd11 on 3/2/17.
 */
@RunWith(TestWithDB.class)
public class SendAllEventsControllerTest {

    private SendAllEventsController sendAllEventsController;

    private ContactService contactService;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private GMailService gmailService;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        contactService = new SqliteContact();
        sendAllEventsController = new SendAllEventsController();
        sendAllEventsController.setGmailService(gmailService);

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
        new Event("Testing-1").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=N/A&event_in_email=N/A", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo1ContactsAsMail() throws Exception {
        contactService.destroyAll();
        new Event("Testing-1").saveIt();
        contactService.addContact(new ContactPerson("testName", "test1@gmail.com", "testLastName"));
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        contactService.destroyAll();
        new Event("Testing-1").saveIt();
        contactService.addContact(new ContactPerson("testName1", "test1@gmail.com", "test1LastName"));
        contactService.addContact(new ContactPerson("testName2", "test2@gmail.com", "test2LastName"));
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=2&event_in_email=1", response.getRedirectedUrl());
    }
}
