package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Notification extends Model {

    private List<NotificationDetail> notificationDetails;

    public Date getSentDate() {
        return (Date) get("sent_at");
    }

    public void setSentDate(Date sentDate) {
        set("sent_at", sentDate);
    }

    public void setNotificationDetails(List<NotificationDetail> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public Notification() {
        notificationDetails = new ArrayList<>();
    }

    public void addEmailAddress(final String emailAddress) {
        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setEmailAddress(emailAddress);

        addNotificationDetail(notificationDetail);
    }

    public void addNotificationDetail(final NotificationDetail notificationDetail) {
        notificationDetails.add(notificationDetail);
    }

    public String getSubject() {
        return (String) get("subject");
    }

    public Long getNotificationId() {
        return (Long) get("notification_id");
    }

    public List<NotificationDetail> getNotificationDetails() {
        return notificationDetails;
    }

    public void setSubject(final String subject) {
        set("subject", subject);
    }

    public void setNotificationId(final Long notificationId) {
        set("notification_id", notificationId);
    }

    public Notification saveAll() {
        saveIt();
        for (NotificationDetail notificationDetail : getNotificationDetails()) {
            notificationDetail.setNotificationId(getNotificationId());
            notificationDetail.saveIt();
        }
        return this;
    }


    public String extract() {
        ArrayList<String> sarray = new ArrayList<String>();
        int count = 0;
        for (NotificationDetail detail : getNotificationDetails()) {
            count += detail.getReadCount();
            sarray.add(detail.toJSON());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        dateFormat.setTimeZone(tz);
        String date = null;
        if (getSentDate() != null) {
            date = dateFormat.format(getSentDate());
        }
        return "{\"subject\":\""+ getSubject()+"\", \"sent_at\":\""+date+"\", \"total_open_count\":"+count+", \"emails\":["+String.join(", ", sarray)+"]}";
    }
}
