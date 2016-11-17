package com.odde.massivemailer.service.impl;


import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.EmailService;
import com.odde.massivemailer.service.NotificationService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class OpenedEmailCounterServiceTest {
    long email_id = 123L;
    EmailService service = new SqliteEmail();
    NotificationServiceSqlite notificationServiceSqlite = new NotificationServiceSqlite();
    EmailCounterTracking emailCounterTracking = new EmailCounterTracking();

    @Before
    public void setUp() {
        service.destroyAll();
    }

    @Test
    public void shouldReturnEmptyJasonWhenNobodyOpenedTheEmail() {
        assertEquals("[]", service.getEmailCounterJson(email_id));
    }

    @Test
    public void shouldReturnRecordWithCountWhenOnePersonOpenedTheEmailOnce1() {
        NotificationService notificationService = new NotificationServiceSqlite();
        Notification notification = new Notification();
        notification.setNotificationId(email_id);
        notification.setSubject("test subject");
        notification.setSentDate(new Date());

        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setEmailAddress("terry@odd-e.com");
        notification.addNotificationDetail(notificationDetail);

        Notification savedNotification = notificationService.save(notification);
        assertEquals("[{'email': 'terry@odd-e.com'; 'count': 0}]", service.getEmailCounterJson(savedNotification.getNotificationId()));
    }
}
