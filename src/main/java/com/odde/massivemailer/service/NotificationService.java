package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.NotificationDetail;

import java.util.ArrayList;

public interface NotificationService {
    Notification save(Notification notification);
}
