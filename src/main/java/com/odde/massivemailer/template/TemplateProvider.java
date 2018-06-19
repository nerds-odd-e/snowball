package com.odde.massivemailer.template;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum TemplateProvider {

    GDPR_TEMPLATE("template/GDPRConsent.ftl");

    private String path;
    TemplateProvider(String path) {
        this.path = path;
    }

    public String getTemplate() throws IOException, URISyntaxException {
        Path path = Paths.get(TemplateProvider.class.getClassLoader().getResource(this.path).toURI());
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }
}
