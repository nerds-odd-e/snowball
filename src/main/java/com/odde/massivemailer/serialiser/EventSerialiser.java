package com.odde.massivemailer.serialiser;

import com.google.gson.*;
import com.odde.massivemailer.model.Event;

import java.lang.reflect.Type;
import java.util.Set;

public class EventSerialiser extends ActiveSerialiser {
    @Override
    protected Set<String> getAttributeNames() {
        return Event.attributeNames();
    }
}
