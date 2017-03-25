package com.odde.massivemailer.serialiser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;

import java.lang.reflect.Type;

public class ContactPersonSerialiser implements JsonSerializer {
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        final ContactPerson contact = (ContactPerson) src;
        final JsonObject jsonObject = new JsonObject();
        for(String f : ContactPerson.attributeNames()) {
            if(contact.get(f) != null) {
                jsonObject.addProperty(f, contact.get(f).toString());
            }
        }

        return jsonObject;
    }
}
