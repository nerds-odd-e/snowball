package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Mail;

import java.util.Set;

public class MockMailService implements MailService {

    @Override
    public void send(Mail email) {
        // do nothing
    }

    @Override
    public Set<Mail> getUnreadEmails() {
        return null;
    }
}
