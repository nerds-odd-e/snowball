package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.Notification;

import java.util.Set;

public class NotificationSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return Notification.attributeNames();
    }
}
