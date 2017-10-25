package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.MailLog;
import com.odde.massivemailer.model.Notification;

import java.util.Set;

public class MailLogSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return MailLog.attributeNames();
    }
}
