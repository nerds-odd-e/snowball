package com.odde.massivemailer.service.impl;


import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.NotificationService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpenedEmailCounterServiceTest {
    int email_id = 123;
    EmailService service = new SqliteEmail();

    @Before
    public void setUp() {
        service.addEmail(email_id, "subject");
    }

    @Test
    public void shouldReturnEmptyJasonWhenNobodyOpenedTheEmail() {
        assertEquals("[]", service.getEmailCounterJson(email_id));
    }

    @Test
    public void shouldReturnRecordWithCountWhenOnePersonOpenedTheEmailOnce() {
        service. increaseCounterOfEmailByOne(email_id, "someone@somewhere.com");
        assertEquals("[{'email': 'someone@somewhere.com'; 'count': 1}]", service.getEmailCounterJson(123));
    }

    @Test
    public void shouldReturnRecordWithCountWhenOnePersonOpenedTheEmailTwice() {
        service.increaseCounterOfEmailByOne(email_id, "someone@somewhere.com");
        service.increaseCounterOfEmailByOne(email_id, "someone@somewhere.com");
        assertEquals("[{'email': 'someone@somewhere.com'; 'count': 2}]", service.getEmailCounterJson(123));
    }

    public void checkEmailCounterIsNotNull(){
        service.


    }


}
