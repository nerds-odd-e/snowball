package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SentMail extends ApplicationModel {

    private List<SentMailVisit> sentMailVisits;

    public Date getSentDate() {
        return (Date) get("sent_at");
    }

    public void setSentDate(Date sentDate) {
        set("sent_at", sentDate);
    }

    public void setSentMailVisits(List<SentMailVisit> sentMailVisits) {
        this.sentMailVisits = sentMailVisits;
    }

    public SentMail() {
        sentMailVisits = new ArrayList<>();
    }

    public void addEmailAddress(final String emailAddress) {
        SentMailVisit sentMailVisit = new SentMailVisit();
        sentMailVisit.setEmailAddress(emailAddress);

        addVisit(sentMailVisit);
    }

    public void addVisit(final SentMailVisit sentMailVisit) {
        sentMailVisits.add(sentMailVisit);
    }

    public String getSubject() {
        return (String) get("subject");
    }

    public String getContent() {
        return (String) get("content");
    }

    public Long getMessageId() {
        return (Long) get("message_id");
    }

    public List<SentMailVisit> getSentMailVisits() {
        return sentMailVisits;
    }

    public void setSubject(final String subject) {
        set("subject", subject);
    }

    public void setMessageId(final Long messageId) {
        set("message_id", messageId);
    }

    public void setContent(String content) {
        set("content", content);
    }

    public SentMail saveAll() {
        saveIt();
        for (SentMailVisit sentMailVisit : getSentMailVisits()) {
            sentMailVisit.setSentMailId(getLongId());
            sentMailVisit.saveIt();
        }
        return this;
    }


    public String extract() {
        ArrayList<String> sarray = new ArrayList<String>();
        int count = 0;
        for (SentMailVisit detail : getSentMailVisits()) {
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
