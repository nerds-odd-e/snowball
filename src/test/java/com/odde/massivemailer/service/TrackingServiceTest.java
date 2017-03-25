package com.odde.massivemailer.service;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;
import com.odde.massivemailer.service.impl.SqliteTracking;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class TrackingServiceTest {
    private TrackingService service;

    @Before
    public void setUp() {
        service = new SqliteTracking();
    }

    @Test
    public void UpdateViewCountMustIncrementByOne() {
        Notification notification = new Notification();
        notification.setSubject("Subject");
        notification.setNotificationId(1234567890L);

        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setEmailAddress("terry@odd-e.com");
        notification.addNotificationDetail(notificationDetail);

        Notification savedNotification = notification.saveAll();
        NotificationDetail savedNotificationDetail = savedNotification.getNotificationDetails().get(0);

        savedNotificationDetail.updateViewCount();

        assertThat(savedNotificationDetail.getRead_count(), is(1));
    }
}
