package com.odde.massivemailer.template;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateProviderTest {
    @Test
    public  void getsHtmlTemplateWhenProvidedTheTemplateName() throws IOException, URISyntaxException {
        String templateBody  = TemplateProvider.getTemplate(TemplateProvider.GDPR_TEMPLATE);
        Assert.assertTrue(templateBody.length() > 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsErrorWhenInvalidTemplateNameIsProvided() throws IOException, URISyntaxException {
        TemplateProvider.getTemplate("blablabla");
    }

}
