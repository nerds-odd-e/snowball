package com.odde.massivemailer.service;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class TemplateServiceTest {

    @Test
    public void applyTemplate() {
        TemplateService templateService = new TemplateService();
        String resolvedTemplate = templateService.createConsentEmailContent(Collections.emptyMap());
        assertTrue("Template should not be empty", resolvedTemplate.length() > 1);
    }

    @Test
    public void ensureFirstNameIsPopulatedOnConsentTemplate() {
        TemplateService templateService = new TemplateService();
        String consentEmailContent = templateService.createConsentEmailContent(Collections.singletonMap("firstName", "Terry"));
        assertThat(consentEmailContent).contains("Dear Terry");
    }
}