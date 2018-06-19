package com.odde.massivemailer.service;

import com.odde.massivemailer.template.TemplateProvider;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TemplateServiceTest {

    @Test()
    public void applyTemplate() throws Exception{
        TemplateService templateService = new TemplateService();
        String resolvedTemplate  = templateService.applyTemplate(TemplateProvider.GDPR_TEMPLATE,null);
        assertTrue("Template should not be empty" , resolvedTemplate.length() > 1);
    }
}