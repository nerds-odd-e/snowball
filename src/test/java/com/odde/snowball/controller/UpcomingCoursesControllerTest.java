package com.odde.snowball.controller;

import com.odde.TestWithDB;
import com.odde.snowball.model.ContactPerson;
import com.odde.snowball.model.Course;
import com.odde.snowball.model.Mail;
import com.odde.snowball.service.GMailService;
import com.odde.snowball.service.UpcomingCourseMailComposer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;

import static com.odde.snowball.factory.ContactFactory.aContactFrom;
import static com.odde.snowball.factory.ContactFactory.uniqueContact;
import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(TestWithDB.class)
public class UpcomingCoursesControllerTest {

    private final Course singaporeEvent = repo(Course.class).fromKeyValuePairs("courseName", "Scrum In Singapore", "city", "Singapore", "country", "Singapore");
    private final Course singaporeEventTwo = repo(Course.class).fromKeyValuePairs("courseName", "A-TDD In Singapore", "city", "Singapore", "country", "Singapore");
    private final Course bangkokEvent = repo(Course.class).fromKeyValuePairs("courseName", "Code Smells In Bangkok", "city", "Bangkok", "country", "Thailand");
    private final Course tokyoEvent = repo(Course.class).fromKeyValuePairs("courseName", "Code Refactoring In Tokyo", "city", "Tokyo", "country", "Japan");

    private final ContactPerson singaporeContact = aContactFrom("Singapore", "Singapore");
    private final ContactPerson singaporeContactTwo = aContactFrom("Singapore", "Singapore");
    private final ContactPerson tokyoContact = aContactFrom("Tokyo", "Japan");
    private final ContactPerson noLocContact= uniqueContact();

    private final ArgumentCaptor<List<Course>> coursesArgument = ArgumentCaptor.forClass(List.class);

    private UpcomingCoursesController upcomingCoursesController;

    private MockHttpServletRequest request;

    private MockHttpServletResponse response;
    @Mock
    private GMailService gmailService;

    @Mock
    private UpcomingCourseMailComposer mailComposer;

    @Mock
    private Mail mail;

    @Before
    public void setUpMockService() {
        MockitoAnnotations.initMocks(this);
        upcomingCoursesController = new UpcomingCoursesController();
        upcomingCoursesController.setMailService(gmailService);
        upcomingCoursesController.setMailComposer(mailComposer);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        when(mailComposer.createUpcomingCourseMail(any(), any())).thenReturn(mail);
    }

    @Test
    public void sendNoEventsToNoContactsAsMail() throws Exception {
        upcomingCoursesController.doPost(request, response);

        assertEquals("course_list.jsp?message=0 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send1EventToNoContactsAsMail() throws Exception {
        singaporeEvent.save();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=0 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo1ContactsAsMail() throws Exception {
        singaporeEvent.save();
        singaporeContact.save();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        singaporeEvent.save();
        singaporeContact.save();
        singaporeContactTwo.save();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=2 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void contactMustReceiveEventInEmailWhenHavingSameLocationAsEvent() throws Exception {
        singaporeEvent.save();
        singaporeContact.save();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void contactMustNotReceiveEventInEmailWhenContactHasNoLocation() throws Exception {
        singaporeEvent.save();
        uniqueContact().save();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=0 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send2EventsTo1ContactSameLocation() throws Exception {
        singaporeEvent.save();
        singaporeEventTwo.save();
        singaporeContact.save();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void bothContactsReceive2EventsWhenHavingSameLocationAs2Events() throws Exception {
        singaporeEvent.save();
        singaporeEventTwo.save();
        singaporeContact.save();
        singaporeContactTwo.save();

        upcomingCoursesController.doPost(request, response);

        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContactTwo), any());
        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContact), any());
    }

    @Test
    public void bothContactsFromSingaporeReceiveOnlyEventInBangkok() throws Exception {
        bangkokEvent.save();
        tokyoEvent.save();
        singaporeContact.save();
        singaporeContactTwo.save();

        upcomingCoursesController.doPost(request, response);

        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContact), any());
        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContactTwo), any());
        verify(mail, times(2)).sendMailWith(gmailService);
    }

    @Test
    public void contactFromTokyoDoesNotReceiveEventInBangkokNorSingapore() throws Exception {
        singaporeEvent.save();
        bangkokEvent.save();
        tokyoContact.save();
        singaporeContact.save();
        upcomingCoursesController.doPost(request, response);

        verify(mailComposer  , times(1)).createUpcomingCourseMail(eq(singaporeContact), coursesArgument.capture());
        verify(mail, times(1)).sendMailWith(gmailService);
        assertEquals(2, coursesArgument.getValue().size());
        assertEquals(singaporeEvent, coursesArgument.getValue().get(0));
    }

    @Test
    public void contactFromTokyoReceiveEventFromTokyoOnly() throws Exception {
        singaporeEvent.save();
        bangkokEvent.save();
        tokyoEvent.save();
        tokyoContact.save();

        upcomingCoursesController.doPost(request, response);

        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(tokyoContact), coursesArgument.capture());
        verify(mail, times(1)).sendMailWith(gmailService);
        assertEquals(1, coursesArgument.getValue().size());
    }

    @Test
    public void contactWWithNoLocationMustNotReceiveMail() throws Exception {
        singaporeEvent.save();
        noLocContact.save();
        upcomingCoursesController.doPost(request, response);
        verify(mail, times(0)).sendMailWith(gmailService);
    }
}