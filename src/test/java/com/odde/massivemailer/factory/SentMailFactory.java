package com.odde.massivemailer.factory;

import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.Template;

import java.util.Date;

public class SentMailFactory {

    public SentMail buildSentMailWithSubject(String subject) {

        Template template = new Template("Template", "", "").saveIt();
        return new SentMail(new Date(), subject, "", 0L, "").saveIt();
    }
}
