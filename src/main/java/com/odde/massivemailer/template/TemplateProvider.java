package com.odde.massivemailer.template;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum TemplateProvider {
    CONSENT_TEMPLATE("template/GDPRConsent.ftl");

    private Template template;

    TemplateProvider(String path) {
        try {
            String templateText = loadTemplate(path);
            template = new Template(path, new StringReader(templateText), new Configuration());
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Template could not be loaded", e);
        }
    }

    private String loadTemplate(String filePath) throws IOException, URISyntaxException {
        Path path = Paths.get(TemplateProvider.class.getClassLoader().getResource(filePath).toURI());
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }

    public static Template getConsentTemplate() {
        return CONSENT_TEMPLATE.getTemplate();
    }

    private Template getTemplate() {
        return template;
    }
}
