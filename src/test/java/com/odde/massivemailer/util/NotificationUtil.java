package com.odde.massivemailer.util;

import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.model.SentMailVisit;

import java.util.ArrayList;
import java.util.List;

public class NotificationUtil {

    private static void addVisit(Mail mail) {

        List<SentMailVisit> sentMailVisits;
        if (mail.getSentMail().getSentMailVisits() == null) {
            sentMailVisits = new ArrayList<>();
            mail.getSentMail().setSentMailVisits(sentMailVisits);
        } else {
            sentMailVisits = mail.getSentMail().getSentMailVisits();
        }

        for (String recipient : mail.getReceipts()) {
            SentMailVisit sentMailVisit = new SentMailVisit();
            sentMailVisit.setEmailAddress(recipient);
            sentMailVisit.setId(System.currentTimeMillis());
            sentMailVisits.add(sentMailVisit);
            mail.getSentMail().setSentMailVisits(sentMailVisits);
        }
    }

    public static void addSentMail(Mail mail) {
        SentMail sentMail = new SentMail();
        sentMail.setId(System.currentTimeMillis());
        mail.setSentMail(sentMail);
        addVisit(mail);
    }
}
