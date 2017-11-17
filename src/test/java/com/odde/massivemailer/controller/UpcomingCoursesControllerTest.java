package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.*;
import com.odde.massivemailer.service.GMailService;
import com.odde.massivemailer.service.UpcomingCourseMailComposer;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(TestWithDB.class)
public class UpcomingCoursesControllerTest {

    private final Course singaporeEvent = new Course("Scrum In Singapore", "", "Singapore/Singapore");
    private final Course singaporeEventTwo = new Course("A-TDD In Singapore", "", "Singapore/Singapore");
    private final Course bangkokEvent = new Course("Code Smells In Bangkok", "", "Bangkok/Thailand");
    private final Course tokyoEvent = new Course("Code Refactoring In Tokyo", "", "Tokyo/Japan");

    private final ContactPerson singaporeContact = new ContactPerson("testName1", "test1@gmail.com", "test1LastName", "", "Singapore/Singapore");
    private final ContactPerson singaporeContactTwo = new ContactPerson("testName2", "test2@gmail.com", "test2LastName", "", "Singapore/Singapore");
    private final ContactPerson tokyoContact = new ContactPerson("testName3", "test3@gmail.com", "test3LastName", "", "Tokyo/Japan");
    private final ContactPerson noLocContact= new ContactPerson("testName4", "test4@gmail.com", "test4LastName", "", null);

    private final ArgumentCaptor<Mail> mailArgument = ArgumentCaptor.forClass(Mail.class);
    private final ArgumentCaptor<List<Course>> coursesArgument = ArgumentCaptor.forClass(List.class);
    private final String linebreak = "<br/>\n";

    private UpcomingCoursesController upcomingCoursesController;

    private MockHttpServletRequest request;

    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String text = date.format(formatter);
    LocalDate parsedDate = LocalDate.parse(text, formatter);

    private MockHttpServletResponse response;
    @Mock
    private GMailService gmailService;

    @Mock
    private UpcomingCourseMailComposer mailComposer;

    @Mock
    private Mail mail;

    @Before
    public void setUpMockService() throws IOException {
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
        singaporeEvent.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=0 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo1ContactsAsMail() throws Exception {
        singaporeEvent.saveIt();
        new ContactPerson("testName", "test1@gmail.com", "testLastName","","Singapore/Singapore").saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send1EventTo2ContactsAsMail() throws Exception {
        singaporeEvent.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=2 emails sent.", response.getRedirectedUrl());
    }


    @Test
    public void contactMustReceiveEventInEmailWhenHavingSameLocationAsEvent() throws Exception {
        singaporeEvent.saveIt();
        singaporeContact.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void contactMustNotReceiveEventInEmailWhenContactHasNoLocation() throws Exception {
        singaporeEvent.saveIt();
        new ContactPerson("testName1", "test1@gmail.com", "test1LastName").saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=0 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void send2EventsTo1ContactSameLocation() throws Exception {
        singaporeEvent.saveIt();
        singaporeEventTwo.saveIt();
        singaporeContact.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void bothContactsReceive2EventsWhenHavingSameLocationAs2Events() throws Exception {
        singaporeEvent.saveIt();
        singaporeEventTwo.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();

        upcomingCoursesController.doPost(request, response);

        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContactTwo), any());
        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContact), any());
    }

    @Test
    public void bothContactsFromSingaporeReceiveOnlyEventInBangkok() throws Exception {
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();
        singaporeContact.saveIt();
        singaporeContactTwo.saveIt();

        upcomingCoursesController.doPost(request, response);

        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContact), any());
        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(singaporeContactTwo), any());
        verify(mail, times(2)).sendMailWith(gmailService);
    }

    @Test
    public void contactFromTokyoDoesNotReceiveEventInBangkokNorSingapore() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoContact.saveIt();
        singaporeContact.saveIt();
        upcomingCoursesController.doPost(request, response);

        verify(mailComposer  , times(1)).createUpcomingCourseMail(eq(singaporeContact), coursesArgument.capture());
        verify(mail, times(1)).sendMailWith(gmailService);
        assertEquals(2, coursesArgument.getValue().size());
        assertEquals(singaporeEvent, coursesArgument.getValue().get(0));
    }

    @Test
    public void contactFromTokyoReceiveEventFromTokyoOnly() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();
        tokyoContact.save();

        upcomingCoursesController.doPost(request, response);

        verify(mailComposer, times(1)).createUpcomingCourseMail(eq(tokyoContact), coursesArgument.capture());
        verify(mail, times(1)).sendMailWith(gmailService);
        assertEquals(1, coursesArgument.getValue().size());
    }

    @Test
    public void contactWWithNoLocationMustNotReceiveMail() throws Exception {
        singaporeEvent.saveIt();
        noLocContact.saveIt();
        upcomingCoursesController.doPost(request, response);
        verify(mail, times(0)).sendMailWith(gmailService);
    }

    @Test
    public void contactMustReceiveDuplicateEmailAfter30DaysForSameCourse() throws Exception {
        singaporeEvent.saveIt();
        ContactPerson person = new ContactPerson("testName", "test1gmail.com", "testLastName", "", "Singapore/Singapore");
        person.saveIt();
        List<Course> nearCourses = Course.findAllCourseNearTo(person.getGeoCoordinates());
        String courseIDs = "";
        for (Course course : nearCourses) {
            courseIDs = courseIDs + "," + course.getId().toString();
        }
        courseIDs = courseIDs.replaceFirst(",","");

        ContactPerson updatePerson = ContactPerson.findById(person.getId());
        updatePerson.setCourseList(courseIDs);
        updatePerson.setSentDate(parsedDate.plusDays(-35).toString());
        updatePerson.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void contactMustNotReceiveDuplicateEmailWithin30DaysForSameCourse() throws Exception {
        singaporeEvent.saveIt();
        ContactPerson person = new ContactPerson("testName", "test1gmail.com", "testLastName", "", "Singapore/Singapore");
        person.saveIt();
        List<Course> nearCourses = Course.findAllCourseNearTo(person.getGeoCoordinates());
        String courseIDs = "";
        for (Course course : nearCourses) {
            courseIDs = courseIDs + "," + course.getId().toString();
        }
        courseIDs = courseIDs.replaceFirst(",","");

        ContactPerson updatePerson = ContactPerson.findById(person.getId());
        updatePerson.setCourseList(courseIDs);
        updatePerson.setSentDate(parsedDate.plusDays(-15).toString());
        updatePerson.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=0 emails sent.", response.getRedirectedUrl());
    }

    @Test
    public void contactMustReceiveDuplicateEmailWithin30DaysForDifferentCourse() throws Exception {
        singaporeEvent.saveIt();
        ContactPerson person = new ContactPerson("testName", "test1gmail.com", "testLastName", "", "Singapore/Singapore");
        person.saveIt();
        List<Course> nearCourses = Course.findAllCourseNearTo(person.getGeoCoordinates());
        String courseIDs = "";
        for (Course course : nearCourses) {
            courseIDs = courseIDs + "," + course.getId().toString();
        }
        courseIDs = courseIDs.replaceFirst(",","");

        ContactPerson updatePerson = ContactPerson.findById(person.getId());
        updatePerson.setCourseList(courseIDs);
        updatePerson.setSentDate(parsedDate.plusDays(-15).toString());
        updatePerson.saveIt();
        singaporeEventTwo.saveIt();
        upcomingCoursesController.doPost(request, response);
        assertEquals("course_list.jsp?message=1 emails sent.", response.getRedirectedUrl());
    }
}