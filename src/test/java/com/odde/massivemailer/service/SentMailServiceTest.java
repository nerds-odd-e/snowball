package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.SentMailVisit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class SentMailServiceTest {

    @Test
    public void NotificationMustBeSaved() {
        SentMail sentMail = new SentMail();
        sentMail.setSubject("Subject");
        sentMail.setContent("content");
        sentMail.setReceivers("");
        sentMail.setMessageId(123456789L);

        SentMail savedSentMail = sentMail.save();

        assertNotNull(savedSentMail);
        assertNotNull(savedSentMail.getId());

        assertThat(savedSentMail.getSubject(), is("Subject"));
        assertThat(savedSentMail.getMessageId(), is(123456789L));
    }

    @Test
    public void SentMailVisitsMustBeSaved() {
        SentMail sentMail = new SentMail();
        sentMail.setSubject("Subject");
        sentMail.setContent("content");
        sentMail.setReceivers("");
        sentMail.setMessageId(123456789L);
        sentMail.save();

        sentMail.addEmailAddress("terry@odd-e.com");

        SentMail savedSentMail = sentMail.save();

        List<SentMailVisit> savedSentMailVisits = savedSentMail.sentMailVisits();

        assertThat(savedSentMailVisits.size(), is(1));

        SentMailVisit savedSentMailVisit = savedSentMailVisits.get(0);

        assertNotNull(savedSentMailVisit.getId());
        assertThat(savedSentMailVisit.getEmailAddress(), is("terry@odd-e.com"));
    }
}
