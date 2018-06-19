package com.odde.massivemailer.service;

import com.odde.massivemailer.template.TemplateProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class TemplateService {
    public String applyTemplate(String templateName, Map<String, Object> parameters) throws IOException, URISyntaxException {
        return TemplateProvider.getTemplate(templateName);
    }
}
