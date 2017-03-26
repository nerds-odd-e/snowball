package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

public class MockMailService extends GMailService {
    public MockMailService(SMTPConfiguration config) {
        super(config);
    }

    @Override
    public void send(Mail email) throws EmailException {
        // do nothing
    }
}
