package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

import java.util.Collection;
import java.util.Set;

public interface MailService {
    void send(Mail email) throws EmailException;

    Set<Mail> getUnreadEmails();
}
