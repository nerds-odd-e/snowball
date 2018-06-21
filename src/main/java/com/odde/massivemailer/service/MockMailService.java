package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Mail;

import java.util.List;

public class MockMailService implements MailService {

    @Override
    public void send(Mail email) {
        // do nothing
    }

    @Override
    public List<Mail> readEmail(boolean readFlag) {
        //do nothing
        return null;
    }

}
