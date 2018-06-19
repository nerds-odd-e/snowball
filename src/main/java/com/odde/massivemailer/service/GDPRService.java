package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;

public class GDPRService {

    private MailService mailService;

    public GDPRService(MailService mailService) {
        this.mailService = mailService;
    }

    public void sendConsentRequestEmail() {
        ContactPerson.getContactsWithoutConsentRequest()
                .forEach(this::sendConsentRequestEmail);
    }

    private void sendConsentRequestEmail(ContactPerson contactPerson) {
        Mail mail = makeConsentRequestMail(contactPerson);

        try {
            mailService.send(mail);
        } catch (EmailException e) {
            throw new RuntimeException("Cannot send email to " + contactPerson.getEmail(), e);
        }
    }

    private Mail makeConsentRequestMail(ContactPerson contactPerson) {
        return new Mail();
    }
}
