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
    NotificationService notificationService = new NotificationServiceSqlite();


    @Before
    public void setUp() {
        service.destroyAll();
    }

    @Test @Ignore
    public void shouldReturnEmptyJasonWhenNobodyOpenedTheEmail() {
        assertEquals("[]", service.getEmailCounterJson(email_id));
    }

    @Test @Ignore
    public void shouldReturnRecordWithCountWhenOnePersonOpenedTheEmail4Times() {
        Notification notification = createNotification();
        addRecipient(notification, "terry@odd-e.com");
        Notification savedNotification = notificationService.save(notification);
        assertEquals("[{'email': 'terry@odd-e.com'; 'count': 4}]", service.getEmailCounterJson(savedNotification.getNotificationId()));
    }

    @Test @Ignore
    public void shouldReturnRecordWithCountWhenTwoPersonOpenedTheEmail() {
        Notification notification = createNotification();
        addRecipient(notification, "terry@odd-e.com");
        addRecipient(notification, "trump@odd-e.com");
        Notification savedNotification = notificationService.save(notification);
        assertEquals("[{'email': 'terry@odd-e.com'; 'count': 4}, {'email': 'trump@odd-e.com'; 'count': 4}]", service.getEmailCounterJson(savedNotification.getNotificationId()));
    }

    private Notification createNotification() {
        Notification notification = new Notification();
        notification.setNotificationId(email_id);
        notification.setSubject("test subject");
        notification.setSentDate(new Date());
        return notification;
    }

    private void addRecipient(Notification notification, String emailAddress) {
        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setEmailAddress(emailAddress);
        notificationDetail.setRead_count(4);
        notification.addNotificationDetail(notificationDetail);
    }
}
