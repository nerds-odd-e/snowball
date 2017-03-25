package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
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


@RunWith(TestWithDB.class)
public class SendAllEventsControllerTest {

    private SendAllEventsController sendAllEventsController;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private GMailService gmailService;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        sendAllEventsController = new SendAllEventsController();
        sendAllEventsController.setGmailService(gmailService);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void sendNoEventsToNoContactsAsMail() throws Exception {
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=N/A&event_in_email=N/A", response.getRedirectedUrl());
    }

    @Test
    public void send1EventToNoContactsAsMail() throws Exception {
        new Event("Testing-1").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=N/A&event_in_email=N/A", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo1ContactsAsMail() throws Exception {
        new Event("Testing-1").saveIt();
        new ContactPerson("testName", "test1@gmail.com", "testLastName").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        new Event("Testing-1").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=2&event_in_email=1", response.getRedirectedUrl());
    }
}
