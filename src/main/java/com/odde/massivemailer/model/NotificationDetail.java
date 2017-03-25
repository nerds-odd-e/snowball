package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

public class NotificationDetail extends Model {
    private Long id;
    private String emailAddress;
    private int read_count;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
        set("email_address", emailAddress);
    }

    public String toJSON() {
        return "{\"email\": \"" + emailAddress + "\", \"open_count\": " + read_count + "}";
    }

    public int getRead_count() {
        Object o = get("read_count");
        if (o != null)
            return (int)o;
        return 0;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
        set("read_count", read_count);
    }

    public void setNotificationId(Long notificationId) {
        set("notification_id", notificationId);
    }

    public void updateViewCount() {
        setRead_count(getRead_count()+1);
        saveIt();
    }
}
