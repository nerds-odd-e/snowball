package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.util.NotificationUtil;
import org.javalite.activejdbc.Base;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.mail.Message;
import javax.mail.Session;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class MailLogTest {

    private final Course singaporeEvent = new Course("Scrum In Singapore", "", "Singapore");
    private final Course bangkokEvent = new Course("Code Smells In Bangkok", "", "Bangkok");
    private final Course tokyoEvent = new Course("Certified Scrum Developer In Tokyo", "", "Tokyo");

    private final ContactPerson singaporeContact = new ContactPerson("testName1", "test1@gmail.com", "test1LastName", "", "Singapore");
    private final ContactPerson bangkokContact = new ContactPerson("testName2", "test2@gmail.com", "test2LastName", "", "Bangkok");

    @Test
    public void testCreate() throws Exception {
        bangkokEvent.saveIt();
        singaporeContact.saveIt();

        MailLog log = new MailLog();
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

        MailLog.createIt("contact_person_id", bangkokEvent.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        MailLog.createIt("contact_person_id", bangkokEvent.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        assertEquals(2, MailLog.findAll().size());
    }

    @Test
    public void testFetchMailLogs() throws Exception {
        singaporeEvent.saveIt();
        bangkokEvent.saveIt();
        tokyoEvent.saveIt();

        singaporeContact.saveIt();

        LocalDateTime now = LocalDateTime.now();

        MailLog.createIt("contact_person_id", singaporeContact.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        MailLog.createIt("contact_person_id", singaporeContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        List<Map> list = MailLog.getReport();
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

        MailLog.createIt("contact_person_id", singaporeContact.getId(), "course_id", singaporeEvent.getId(), "sent_at", now);
        MailLog.createIt("contact_person_id", singaporeContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);
        MailLog.createIt("contact_person_id", bangkokContact.getId(), "course_id", bangkokEvent.getId(), "sent_at", now);

        List<Map> list = MailLog.getReport();
        assertEquals(2, list.size());
        assertEquals(2, list.get(0).get("course_count"));
        assertEquals(1, list.get(1).get("course_count"));
    }
}

