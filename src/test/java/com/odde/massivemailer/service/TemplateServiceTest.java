package com.odde.massivemailer.service;

import com.odde.massivemailer.template.TemplateProvider;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class TemplateServiceTest {

    @Test()
    public void applyTemplate() throws Exception{
        TemplateService templateService = new TemplateService();
        String resolvedTemplate  = templateService.applyTemplate(TemplateProvider.GDPR_TEMPLATE,null);
        assertTrue("Template should not be empty" , resolvedTemplate.length() > 1);
    }

    @Test()
    public void applyNameIntoTemplate() throws Exception{
        TemplateService templateService = new TemplateService();
        String name = "abc";
        Map<String, Object> parameter = Collections.singletonMap("firstName", name);

        String resolvedTemplate  = templateService.applyTemplate(TemplateProvider.GDPR_TEMPLATE, parameter);

        assertTrue("GDPR_TEMPLATE should contain the first name of receipt.", resolvedTemplate.contains(name));
    }
}