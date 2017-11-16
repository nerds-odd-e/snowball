package com.odde.massivemailer;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.Template;

import java.util.HashMap;
import java.util.Map;

public class SentMailDataMother {

    private SentMail sentMail;
    private Template template;

    public SentMail buildSentMailWithSubject(String subject) {

        Template template = Template.createIt("TemplateName", "Template");
        return SentMail.createIt("template_id", template.getId(), "subject", subject);
    }
}
