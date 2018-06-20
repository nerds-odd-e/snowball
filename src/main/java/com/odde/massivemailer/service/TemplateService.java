package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.template.TemplateProvider;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class TemplateService {
    public String createConsentEmailContent(ContactPerson contactPerson) {
        Template consentTemplate = TemplateProvider.getConsentTemplate();
        StringWriter contentWriter = new StringWriter();
        try {
            consentTemplate.process(contactPerson, contentWriter);
        } catch (TemplateException | IOException e) {
            throw new IllegalStateException(e);
        }

        return contentWriter.getBuffer().toString();
    }
}
