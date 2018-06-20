package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class TemplateServiceTest {

    @Test
    public void applyTemplate() {
        TemplateService templateService = new TemplateService();
        String resolvedTemplate = templateService.createConsentEmailContent(new ContactPerson());
        assertTrue("Template should not be empty", resolvedTemplate.length() > 1);
    }

    @Test
    public void ensureFirstNameIsPopulatedOnConsentTemplate() {
        TemplateService templateService = new TemplateService();
        String consentEmailContent = templateService.createConsentEmailContent(new ContactPerson("Terry", "terry@odde.com", "Tan"));
        assertThat(consentEmailContent).contains("Dear Terry");
    }
}