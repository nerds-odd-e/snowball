package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.GMailService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


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
        sendAllEventsController.setMailService(gmailService);

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
        new ContactPerson("testName", "test1@gmail.com", "testLastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        new Event("Testing-1").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=2&event_in_email=2", response.getRedirectedUrl());
    }

    @Test
    public void contactMustReceiveEventInEmailWhenHavingSameLocationAsEvent() throws Exception {
        new Event("Testing-1","","Singapore").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void contactMustNotReceiveEventInEmailWhenContactHasNoLocation() throws Exception {
        new Event("Testing-1","","Singapore").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=0&event_in_email=0", response.getRedirectedUrl());
    }

    @Test
    public void send2EventsTo1ContactSameLocation() throws Exception {
        new Event("Testing-1","","Singapore").saveIt();
        new Event("Testing-2","","Singapore").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=2", response.getRedirectedUrl());
    }

    @Test @Ignore
    public void bothContactsReceive2EventsWhenHavingSameLocationAs2Events() throws Exception {
        new Event("Testing-1","","Singapore").saveIt();
        new Event("Testing-2","","Singapore").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(gmailService, times(2)).send(argument.capture());
        for (Mail mail: argument.getAllValues()) {
            assertEquals("Testing-1<br/>\n" +
                        "Testing-2", mail.getContent());
        }
    }

    @Test @Ignore
    public void bothContactsReceive2EventsInCloseProximity() throws Exception {
        new Event("Scrum in Singapore","","Singapore").saveIt();
        new Event("ATDD In Bangkok","","Bangkok").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(gmailService, times(2)).send(argument.capture());
        for (Mail mail: argument.getAllValues()) {
            assertEquals("Scrum in Singapore<br/>\n" +
                    "ATDD In Bangkok", mail.getContent());
        }
    }

    @Test @Ignore
    public void bothContactsFromSingaporeReceiveOnlyEventInBangkok() throws Exception {
        new Event("Testing-1","","Bangkok").saveIt();
        new Event("Testing-2","","Tokyo").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        ArgumentCaptor<Mail> argument = ArgumentCaptor.forClass(Mail.class);
        verify(gmailService, times(2)).send(argument.capture());

        for (Mail mail: argument.getAllValues()) {
            assertEquals("Testing-1", mail.getContent());
        }
    }

    @Test
    /**
     * Pre-cond :
     * Contact A in Singapore and Contact B in Bangkok
     * Events A and B are in Singapore, Event C in Bangkok
     * Post-cond:
     * Contact A will receive Events A and B, but not Event C
     * Contact B will receive Event C, but not Events A and B
     */
    public void eachContactMustReceiveEventsCorrespondingToOnesLocation() throws Exception {
        new Event("Testing-1","","Singapore").saveIt();
        new Event("Testing-2","","Singapore").saveIt();
        new Event("Testing-3","","Bangkok").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName","", "Bangkok").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=2&event_in_email=3", response.getRedirectedUrl());
    }

    @Test
    /**
     * Pre-cond :
     * Contact A in Singapore and Contact B has no location
     * Event A in Singapore and Event B in Bangkok
     * Post-cond:
     * Contact A will receive Event A, but not Event B
     * Contact B will not receive any mail
     */
    public void contactWWithNoLocationMustNotReceiveMail() throws Exception {
        new Event("Testing-1","","Singapore").saveIt();
        new Event("Testing-2","","Bangkok").saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName","","Singapore").saveIt();
        new ContactPerson("testName2", "test2@gmail.com", "test2LastName","").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

}
