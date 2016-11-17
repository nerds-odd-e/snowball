package com.odde.massivemailer.service.impl;


import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.NotificationService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OpenedEmailCounterServiceTest {
    int email_id = 123;
    EmailService service = new SqliteEmail();
    NotificationServiceSqlite notificationServiceSqlite = new NotificationServiceSqlite();
    EmailCounterTracking emailCounterTracking = new EmailCounterTracking();

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



    public void checkEmailCounterIsNotNull(){
//        NotificationDetail notificationDetail = new NotificationDetail();
//        notificationDetail.setId(1L);
//        notificationDetail.setEmailAddress("bteo618@hotmail.com");
//
//        Notification notification = new Notification();
//        notification.setId(1L);
//        notification.addNotificationDetail(notificationDetail);
//
//        notificationServiceSqlite.save(notification);
//
//        emailCounterTracking.retrieveNotificationDetail()
//
//
    }


}
