package com.odde.massivemailer.util;

import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;

import java.util.ArrayList;
import java.util.List;

public class NotificationUtil {

    private static void addNotificationDetail(Mail mail) {

        List<NotificationDetail> notificationDetails = null;
        if (mail.getNotification().getNotificationDetails() == null) {
            notificationDetails = new ArrayList<>();
            mail.getNotification().setNotificationDetails(notificationDetails);
        } else {
            notificationDetails = mail.getNotification().getNotificationDetails();
        }

        for (String recipient : mail.getReceipts()) {
            NotificationDetail notificationDetail = new NotificationDetail();
            notificationDetail.setEmailAddress(recipient);
            notificationDetail.setId(System.currentTimeMillis());
            notificationDetails.add(notificationDetail);
            mail.getNotification().setNotificationDetails(notificationDetails);
        }
    }

    public static void addNotification(Mail mail) {
        Notification notification = new Notification();
        notification.setId(System.currentTimeMillis());
        mail.setNotification(notification);
        addNotificationDetail(mail);
    }
}
