package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.CourseContactNotification;

import java.util.Set;

public class MailLogSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return CourseContactNotification.attributeNames();
    }
}
