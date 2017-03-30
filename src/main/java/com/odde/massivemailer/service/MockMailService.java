package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Mail;

public class MockMailService implements MailService {

    @Override
    public void send(Mail email) {
        // do nothing
    }
}
