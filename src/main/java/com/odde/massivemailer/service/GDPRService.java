package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import edu.emory.mathcs.backport.java.util.Collections;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.odde.massivemailer.template.TemplateProvider.GDPR_TEMPLATE;

public class GDPRService {

    private MailService mailService;
    private TemplateService templateService;

    public GDPRService(MailService mailService, TemplateService templateService) {
        this.mailService = mailService;
        this.templateService = templateService;
    }

    public void sendConsentRequestEmail() {
        ContactPerson.getContactsWithoutConsentRequest().stream()
                .map(this::makeConsentRequestMail)
                .forEach(this::sendConsentRequestEmail);
    }

    private void sendConsentRequestEmail(Mail mail) {
        try {
            mailService.send(mail);
        } catch (EmailException e) {
            throw new RuntimeException("Cannot send email to " + mail.getReceipts(), e);
        }
    }

    private Mail makeConsentRequestMail(ContactPerson contactPerson) {
        String content = null;
        try {
            content = templateService.applyTemplate(GDPR_TEMPLATE, Collections.singletonMap("firstName", contactPerson.getLastname()));
        } catch (IOException | URISyntaxException | TemplateException e) {
            throw new RuntimeException("Template processing failed.", e);
        }

        return Mail.createConsentRequestEmail(content, contactPerson.getEmail());
    }
}
