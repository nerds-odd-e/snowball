package com.odde.massivemailer.serialiser;

import com.odde.massivemailer.model.ContactPerson;

import java.util.Set;

public class ContactPersonSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return ContactPerson.attributeNames();
    }
}
