package com.odde.massivemailer.model;

public class Notification {
    private Long id;
    private String subject;
    private Integer notificationId;

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setNotificationId(final Integer notificationId) {
        this.notificationId = notificationId;
    }
}
