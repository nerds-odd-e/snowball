package com.odde.massivemailer.model;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private Long id;
    private String subject;
    private Long notificationId;
    private List<NotificationDetail> notificationDetails;

    public Notification() {
        notificationDetails = new ArrayList<>();
    }

    public void addEmailAddress(final String emailAddress) {
        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setEmailAddress(emailAddress);

        notificationDetails.add(notificationDetail);
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public List<NotificationDetail> getNotificationDetails() {
        return notificationDetails;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setNotificationId(final Long notificationId) {
        this.notificationId = notificationId;
    }
}
