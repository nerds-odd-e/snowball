package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.SentMail;

import java.util.Set;

public class NotificationSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return SentMail.attributeNames();
    }
}
