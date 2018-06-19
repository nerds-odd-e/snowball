package com.odde.massivemailer.service;

import com.odde.massivemailer.template.TemplateProvider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class TemplateService {
    public String applyTemplate(TemplateProvider templateProvider, Map<String, Object> parameters) throws IOException, URISyntaxException {
        // TODO: apply parameter into template
        return templateProvider.getTemplate();
    }
}
