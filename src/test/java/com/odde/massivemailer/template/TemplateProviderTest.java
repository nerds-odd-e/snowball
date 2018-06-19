package com.odde.massivemailer.template;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateProviderTest {
    @Test
    public  void getsHtmlTemplateWhenProvidedTheTemplateName() throws IOException, URISyntaxException {
        String templateBody  = TemplateProvider.GDPR_TEMPLATE.getTemplate();
        Assert.assertTrue(templateBody.length() > 1);
    }
}
