package com.odde.massivemailer.service.impl;


import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.NotificationService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpenedEmailCounterServiceTest {
    int email_id = 123;

    @Test
    public void shouldReturnEmptyJasonWhenNobodyOpenedTheEmail() {
        EmailService service = new SqliteEmail();
        service.addEmail(email_id, "subject");
        assertEquals("[]", service.getEmailCounterJson(email_id));
    }

    @Test
    public void shouldReturnRecordWithCountWhenOnePersonOpenedTheEmailOnce() {
        EmailService service = new SqliteEmail();
        service.addEmail(email_id, "subject");
        service. increaseCounterOfEmailByOne(email_id, "someone1@somehwere.com");
        assertEquals("[{'email': 'someone1@somewhere.com'; 'count': 1}]", service.getEmailCounterJson(123));
    }
}
