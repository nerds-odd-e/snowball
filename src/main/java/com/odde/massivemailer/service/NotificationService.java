package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;

import java.util.ArrayList;

public interface NotificationService {
    Notification save(Notification notification);

    void add(int email_id, String recipient_email);

    ArrayList<NotificationDetail> getReceipentOfEmail(int email_id);
}
