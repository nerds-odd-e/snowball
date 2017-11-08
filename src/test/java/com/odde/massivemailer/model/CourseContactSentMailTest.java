package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.*;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class CourseContactSentMailTest {

    private final Course singaporeEvent = new Course("Scrum In Singapore", "", "Singapore");
    private final Course bangkokEvent = new Course("Code Smells In Bangkok", "", "Bangkok");
    private final Course tokyoEvent = new Course("Certified Scrum Developer In Tokyo", "", "Tokyo");

    private final ContactPerson singaporeContact = new ContactPerson("testName1", "test1@gmail.com", "test1LastName", "", "Singapore");
    private final ContactPerson bangkokContact = new ContactPerson("testName2", "test2@gmail.com", "test2LastName", "", "Bangkok");

    @Test
    public void testCreate() throws Exception {
        bangkokEvent.saveIt();
        singaporeContact.saveIt();

        CourseContactNotification log = new CourseContactNotification();
        log.set("contact_person_id", singaporeContact.getId());
        log.set("course_id", bangkokEvent.getId());
        log.set("sent_at", Instant.parse("2015-12-15T23:30:00.000Z"));

        assertEquals(log.get("sent_at"),Instant.parse("2015-12-15T23:30:00.000Z"));
    }

    @Test
    public void testFindAllMailLogs() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();
        singaporeContact.saveIt();
        LocalDateTime now = LocalDateTime.now();

        CourseContactNotification.createIt("contact_person_id", bangkokEvent.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        CourseContactNotification.createIt("contact_person_id", bangkokEvent.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        assertEquals(2, CourseContactNotification.findAll().size());
    }

    @Test
    public void testFetchMailLogs() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();

        singaporeContact.saveIt();

        LocalDateTime now = LocalDateTime.now();

        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        List<Map> list = CourseContactNotification.getReport();
        assertEquals(1, list.size());
        assertEquals(2, list.get(0).get("course_count"));
    }

    @Test
    public void testFetchMailLogsMultiContact() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();

        singaporeContact.saveIt();
        bangkokContact.saveIt();

        LocalDateTime now = LocalDateTime.now();

        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);
        CourseContactNotification.createIt("contact_person_id", bangkokContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        List<Map> list = CourseContactNotification.getReport();
        assertEquals(2, list.size());
        assertEquals(2, list.get(0).get("course_count"));
        assertEquals(1, list.get(1).get("course_count"));
    }

    @Test
    public void testMailLogIsExist() throws Exception {
        singaporeContact.saveIt();

        singaporeEvent.saveIt();
        bangkokEvent.saveIt();

        LocalDateTime now = LocalDateTime.now();
        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        List<Course> courses = new ArrayList<>();
        courses.add(singaporeEvent);
        courses.add(bangkokEvent);

        assertEquals(true, CourseContactNotification.isExist(singaporeContact, courses));
    }

    @Test
    public void testAllMailLogIsNotExist() throws Exception {
        singaporeContact.saveIt();

        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        Course newBangkokEvent = new Course("New Beer event in Bangkok", "", "Bangkok");
        newBangkokEvent.saveIt();

        LocalDateTime now = LocalDateTime.now();
        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        CourseContactNotification.createIt("contact_person_id", singaporeContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        List<Course> courses = new ArrayList<>();
        courses.add(singaporeEvent);
        courses.add(bangkokEvent);
        courses.add(newBangkokEvent);

        assertEquals(false, CourseContactNotification.isExist(singaporeContact, courses));
    }

}

