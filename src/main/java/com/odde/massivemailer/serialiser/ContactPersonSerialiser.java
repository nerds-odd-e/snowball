package com.odde.massivemailer.serialiser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import org.javalite.activejdbc.Model;

import java.lang.reflect.Type;
import java.util.Set;

public class ContactPersonSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return ContactPerson.attributeNames();
    }
}
