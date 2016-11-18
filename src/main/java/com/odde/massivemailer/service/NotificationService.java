package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;

import java.util.List;

public interface NotificationService {
    Notification save(Notification notification);

    List<NotificationDetail> getNotificationDetails(Long notificationId);

    Notification getNotification(Long id);
}
