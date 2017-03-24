package com.odde.massivemailer.serialiser;

import com.google.gson.*;
import com.odde.massivemailer.model.Event;

import java.lang.reflect.Type;

public class EventSerialiser implements JsonSerializer {
    @Override
        public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        final Event event = (Event) src;
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", event.getTitle());
        jsonObject.addProperty("content", event.getContent());
        return jsonObject;
    }
}
