package com.odde.massivemailer.model;

public class NotificationDetail {
    private Long id;
    private String emailAddress;
    private int read_count;

    public Long getId() {
        return id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String toJSON() {
        return "{\"email\": \"" + emailAddress + "\", \"open_count\": " + read_count + "}";
    }

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }
}
