package com.odde.massivemailer.service.impl;


import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.SentMailVisit;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class OpenedEmailCounterServiceTest {
    long email_id = 123L;

    @Test
    public void shouldReturnEmptyJasonWhenNobodyOpenedTheEmail() {
        SentMail sentMail = new SentMail();
        String json = sentMail.extract();
        assertEquals("{\"subject\":\"null\", \"sent_at\":\"null\", \"total_open_count\":0, \"emails\":[]}", json);
    }

    @Test
    public void shouldReturnRecordWithCountWhenOnePersonOpenedTheEmail4Times() {
        SentMail sentMail = createNotification();
        addRecipient(sentMail, "terry@odd-e.com");
        String json = sentMail.extract();

        assertEquals("{\"subject\":\"test subject\", \"sent_at\":\"2016-11-18\", \"total_open_count\":4, \"emails\":[{\"email\": \"terry@odd-e.com\", \"open_count\": 4}]}", json);
    }

    @Test
    public void shouldReturnRecordWithCountWhenTwoPersonOpenedTheEmail() {
        SentMail sentMail = createNotification();
        addRecipient(sentMail, "terry@odd-e.com");
        addRecipient(sentMail, "trump@odd-e.com");

        String json = sentMail.extract();
        assertEquals("{\"subject\":\"test subject\", \"sent_at\":\"2016-11-18\", \"total_open_count\":8, \"emails\":[{\"email\": \"terry@odd-e.com\", \"open_count\": 4}, {\"email\": \"trump@odd-e.com\", \"open_count\": 4}]}", json);
    }

    private SentMail createNotification() {
        SentMail sentMail = new SentMail();
        sentMail.setMessageId(email_id);
        sentMail.setSubject("test subject");
        String input = "Fri Nov 18 03:19:03 SGT 2016";
        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        parser.setTimeZone(tz);
        try {
            sentMail.setSentDate(parser.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sentMail;
    }

    private void addRecipient(SentMail sentMail, String emailAddress) {
        SentMailVisit sentMailVisit = new SentMailVisit();
        sentMailVisit.setEmailAddress(emailAddress);
        sentMailVisit.setReadCount(4);
        sentMail.addVisit(sentMailVisit);
    }
}
