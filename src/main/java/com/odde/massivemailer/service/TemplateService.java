package com.odde.massivemailer.service;

import com.odde.massivemailer.template.TemplateProvider;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;

public class TemplateService {
    public String applyTemplate(TemplateProvider templateProvider, Map<String, Object> parameters) throws IOException, URISyntaxException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));

        Template t = new Template("templateName", templateProvider.getTemplate(), cfg);

        Writer out = new StringWriter();
        t.process(parameters, out);

        return out.toString();
    }
}
