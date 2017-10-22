package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.GMailService;
import org.javalite.activejdbc.Base;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(TestWithDB.class)
public class SendAllEventsControllerTest {

    private final Course singaporeEvent = new Course("Scrum In Singapore", "", "Singapore");
    private final Course singaporeEventTwo = new Course("A-TDD In Singapore", "", "Singapore");
    private final Course bangkokEvent = new Course("Code Smells In Bangkok", "", "Bangkok");
    private final Course tokyoEvent = new Course("Code Refactoring In Tokyo", "", "Tokyo");

    private final ContactPerson singaporeContact = new ContactPerson("testName1", "test1@gmail.com", "test1LastName", "", "Singapore");
    private final ContactPerson singaporeContactTwo = new ContactPerson("testName2", "test2@gmail.com", "test2LastName", "", "Singapore");
    private final ContactPerson tokyoContact = new ContactPerson("testName3", "test3@gmail.com", "test3LastName", "", "Tokyo");
    private final ContactPerson noLocContact= new ContactPerson("testName4", "test4@gmail.com", "test4LastName", "", null);

    private final ArgumentCaptor<Mail> mailArgument = ArgumentCaptor.forClass(Mail.class);
    private final String linebreak = "<br/>\n";

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
        assertEquals("eventlist.jsp?email_sent=0&event_in_email=0", response.getRedirectedUrl());
    }

    @Test
    public void send1EventToNoContactsAsMail() throws Exception {
        new Event("Testing-1").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=0&event_in_email=0", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo1ContactsAsMail() throws Exception {
        singaporeEvent.saveIt();
        new ContactPerson("testName", "test1@gmail.com", "testLastName","","Singapore").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        singaporeEvent.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=2&event_in_email=2", response.getRedirectedUrl());
    }


    @Test
    public void contactMustReceiveEventInEmailWhenHavingSameLocationAsEvent() throws Exception {
        singaporeEvent.saveIt();
        singaporeContact.saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=1", response.getRedirectedUrl());
    }

    @Test
    public void contactMustNotReceiveEventInEmailWhenContactHasNoLocation() throws Exception {
        singaporeEvent.saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName").saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=0&event_in_email=0", response.getRedirectedUrl());
    }

    @Test
    public void send2EventsTo1ContactSameLocation() throws Exception {
        singaporeEvent.saveIt();
        singaporeEventTwo.saveIt();
        singaporeContact.saveIt();
        sendAllEventsController.doPost(request, response);
        assertEquals("eventlist.jsp?email_sent=1&event_in_email=2", response.getRedirectedUrl());
    }

    @Test
    public void bothContactsReceive2EventsWhenHavingSameLocationAs2Events() throws Exception {
        singaporeEvent.saveIt();
        singaporeEventTwo.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();

        sendAllEventsController.doPost(request, response);

        verify(gmailService, times(2)).send(mailArgument.capture());
        for (Mail mail: mailArgument.getAllValues()) {
            assertEquals(singaporeEvent.getCoursename() + linebreak +
                        singaporeEventTwo.getCoursename(), mail.getContent());
        }
    }

    @Test
    public void bothContactsReceive2EventsInCloseProximity() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();

        sendAllEventsController.doPost(request, response);

        verify(gmailService, times(2)).send(mailArgument.capture());
        for (Mail mail: mailArgument.getAllValues()) {
            assertEquals(singaporeEvent.getCoursename() + linebreak +
                    bangkokEvent.getCoursename(), mail.getContent());
        }
    }

    @Test
    public void bothContactsFromSingaporeReceiveOnlyEventInBangkok() throws Exception {
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();

        sendAllEventsController.doPost(request, response);

        verify(gmailService, times(2)).send(mailArgument.capture());
        for (Mail mail: mailArgument.getAllValues()) {
            assertEquals(bangkokEvent.getCoursename(), mail.getContent());
        }
    }

    @Test
    public void contactFromTokyoDoesNotReceiveEventInBangkokNorSingapore() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoContact.saveIt();
        singaporeContact.saveIt();
        sendAllEventsController.doPost(request, response);

        verify(gmailService, times(1)).send(mailArgument.capture());

        for (Mail mail: mailArgument.getAllValues()) {
            assertEquals(singaporeEvent.getCoursename() + linebreak +
                    bangkokEvent.getCoursename(), mail.getContent());
            assertEquals(singaporeContact.getEmail(), mail.getReceipts().get(0));
            assertEquals(1, mail.getReceipts().size());
        }
    }

    @Test
    public void contactFromTokyoReceiveEventFromTokyoOnly() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();
        tokyoContact.save();

        sendAllEventsController.doPost(request, response);

        verify(gmailService, times(1)).send(mailArgument.capture());

        for (Mail mail: mailArgument.getAllValues()) {
            assertEquals(tokyoEvent.getCoursename(), mail.getContent());
            assertEquals(tokyoContact.getEmail(), mail.getReceipts().get(0));
            assertEquals(1, mail.getReceipts().size());
        }

    }

    @Test
    public void contactWWithNoLocationMustNotReceiveMail() throws Exception {
        singaporeEvent.saveIt();
        noLocContact.saveIt();
        sendAllEventsController.doPost(request, response);
        verify(gmailService, times(0)).send(mailArgument.capture());
    }


}
