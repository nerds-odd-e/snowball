package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.impl.NotificationServiceSqlite;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class NotificationServiceTest {
    private NotificationService service;

    @Before
    public void setUp() {
        service = new NotificationServiceSqlite();
    }

    @Test
    public void NotificationMustBeSaved() {
        Notification notification = new Notification();
        notification.setSubject("Subject");
        notification.setNotificationId(123456789L);

        Notification savedNotification = service.save(notification);

        assertNotNull(savedNotification);
        assertNotNull(savedNotification.getId());

        assertThat(savedNotification.getSubject(), is("Subject"));
        assertThat(savedNotification.getNotificationId(), is(123456789L));
    }

    @Test
    public void NotificationDetailsMustBeSaved() {
        Notification notification = new Notification();
        notification.setSubject("Subject");
        notification.setNotificationId(123456789L);

        notification.addEmailAddress("terry@odd-e.com");

        Notification savedNotification = service.save(notification);

        List<NotificationDetail> savedNotificationDetails = savedNotification.getNotificationDetails();

        assertThat(savedNotificationDetails.size(), is(1));

        NotificationDetail savedNotificationDetail = savedNotificationDetails.get(0);

        assertNotNull(savedNotificationDetail.getId());
        assertThat(savedNotificationDetail.getEmailAddress(), is("terry@odd-e.com"));
    }
}
