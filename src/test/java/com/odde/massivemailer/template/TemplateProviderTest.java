package com.odde.massivemailer.template;

import freemarker.template.Template;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateProviderTest {

    @Test
    public void createFreemarkTemplateForGDPRConsent() {
        Template template = TemplateProvider.getConsentTemplate();
        assertThat(template.getName()).isEqualTo("template/GDPRConsent.ftl");
    }
}
