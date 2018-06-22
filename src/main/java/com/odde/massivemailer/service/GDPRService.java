package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.SentMail;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

public class GDPRService {
    private final Logger logger = Logger.getLogger(GDPRService.class.getName());

    private MailService mailService;
    private TemplateService templateService;
    private ConsentService consentService;
    public GDPRService(MailService mailService, TemplateService templateService, ConsentService consentService) {
        this.mailService = mailService;
        this.templateService = templateService;
        this.consentService = consentService;
    }

    public void sendConsentRequestEmail() {
        ContactPerson.getContactsWithoutConsentRequest().stream()
                .map(this::updateConsentRequestDate)
                .map(this::makeConsentRequestMail)
                .forEach(this::sendConsentRequestEmail);
    }

    private ContactPerson updateConsentRequestDate(ContactPerson contactPerson) {
        this.consentService.updateConsentRequestDate(contactPerson);
        return contactPerson;
    }

    private void sendConsentRequestEmail(Mail mail) {
        try {
            SentMail sentMail = mail.asSentMail().saveAll();
            mail.setSentMail(sentMail);
            mailService.send(mail);
        } catch (EmailException e) {
            throw new RuntimeException("Cannot send email to " + mail.getReceipts(), e);
        }
    }

    private Mail makeConsentRequestMail(ContactPerson contactPerson) {
        String content = templateService.createConsentEmailContent(contactPerson);
        return Mail.createConsentRequestEmail(content, contactPerson.getEmail());
    }

    public void processGDPRConsentEmails() {
        try {
            mailService.readEmail(false).stream()
                    .filter(mail -> {
                        try {
                            return mail.getSubject().contains("GDPR Consent");
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(mail -> processResponse(mail));

        } catch (MessagingException ex) {
            System.out.println("Could not read the emails");
        }
    }

    private void processResponse(Message mail) {
        try {
            ContactPerson contactByEmail = ContactPerson.getContactByEmail(mail.getFrom()[0].toString());
            if (mail.getContent().toString().contains("I Agree")) {
                contactByEmail.setConsentReceived(LocalDate.now());
                contactByEmail.save();
            }
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<Mail> getEmails() {
        return Collections.emptyList();
    }

    public boolean canContactReceiveEmail(ContactPerson contactPerson) {
        return  !contactPerson.isForgotten();
    }
}
