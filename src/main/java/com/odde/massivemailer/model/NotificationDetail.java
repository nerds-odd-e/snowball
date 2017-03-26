package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

public class NotificationDetail extends Model {
    public String getEmailAddress() {
        return (String) get("email_address");
    }

    public void setEmailAddress(final String emailAddress) {
        set("email_address", emailAddress);
    }

    public String toJSON() {
        return "{\"email\": \"" + getEmailAddress() + "\", \"open_count\": " + getReadCount() + "}";
    }

    public int getReadCount() {
        Object o = get("read_count");
        if (o != null)
            return (int)o;
        return 0;
    }

    public void setReadCount(int read_count) {
        set("read_count", read_count);
    }

    public void setNotificationId(Long notificationId) {
        set("notification_id", notificationId);
    }

    public void updateViewCount() {
        setReadCount(getReadCount()+1);
        saveIt();
    }
}
