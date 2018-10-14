package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.Template;

public class SentMailFactory {

    public SentMail buildSentMailWithSubject(String subject) {

        Template template = Template.createIt("TemplateName", "Template");
        return SentMail.createIt("template_id", template.getId(), "subject", subject);
    }
}
