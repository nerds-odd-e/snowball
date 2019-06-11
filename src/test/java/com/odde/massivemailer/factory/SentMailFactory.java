package com.odde.snowball.factory;

import com.odde.snowball.model.SentMail;
import com.odde.snowball.model.Template;

import java.util.Date;

public class SentMailFactory {

    public SentMail buildSentMailWithSubject(String subject) {

        Template template = new Template("Template", "", "").save();
        return new SentMail(new Date(), subject, "", 0L, "").save();
    }
}
