package com.odde.massivemailer.model;

public class Notification {
    private Long id;
    private String subject;
    private Long notificationId;

    public Notification() {
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
