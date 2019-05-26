package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.Template;

public class SentMailFactory {

    public SentMail buildSentMailWithSubject(String subject) {

        Template template = new Template("Template", "", "").saveIt();
        return SentMail.createIt("template_id", template.getId().toString(), "subject", subject);
    }
}
