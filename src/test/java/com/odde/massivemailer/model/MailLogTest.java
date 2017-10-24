package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.util.NotificationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class MailLogTest {

    private final Course bangkokEvent = new Course("Code Smells In Bangkok", "", "Bangkok");

    private final ContactPerson singaporeContact = new ContactPerson("testName1", "test1@gmail.com", "test1LastName", "", "Singapore");

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
}

