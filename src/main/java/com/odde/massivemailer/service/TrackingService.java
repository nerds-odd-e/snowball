package com.odde.massivemailer.service;

/**
 * Created by csd11 on 15/11/16.
 */
public interface TrackingService {

    void updateViewCount(String notificationId, String emailId);

    void destroyAll();
}
