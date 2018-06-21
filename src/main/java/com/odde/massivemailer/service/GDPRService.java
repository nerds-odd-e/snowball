package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.SentMail;

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
        logger.info("Processing GDPR consent response emails.");
    }

    public Collection<Mail> getEmails() {
        return Collections.emptyList();
    }

    public boolean canContactReceiveEmail(ContactPerson contactPerson) {
        return !contactPerson.isForgotten();
    }
}
