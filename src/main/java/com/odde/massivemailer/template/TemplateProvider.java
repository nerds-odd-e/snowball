package com.odde.massivemailer.template;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TemplateProvider {
    public static final String GDPR_TEMPLATE = "GDPR Consent";

    public static String getTemplate(String templateName) throws IOException, URISyntaxException {

        switch (templateName) {
            case GDPR_TEMPLATE:
               Path path = Paths.get(TemplateProvider.class.getClassLoader().getResource("template/GDPRConsent.ftl").toURI());
                byte[] fileBytes = Files.readAllBytes(path);
                    return new String(fileBytes);
                default:
                    throw new IllegalArgumentException(String.format("Template %s is not defined",templateName));
        }
    }
}
