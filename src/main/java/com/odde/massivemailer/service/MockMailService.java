package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Mail;

import javax.mail.Message;
import java.util.List;

public class MockMailService implements MailService {

    @Override
    public void send(Mail email) {
        // do nothing
    }

    @Override
    public List<Message> readEmail(boolean readFlag) {
        //do nothing
        return null;
    }

}
