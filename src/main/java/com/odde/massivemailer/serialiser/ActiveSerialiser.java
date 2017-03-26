package com.odde.massivemailer.serialiser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.javalite.activejdbc.Model;

import java.lang.reflect.Type;
import java.util.Set;

public abstract class ActiveSerialiser implements JsonSerializer {
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
            return getJsonElement((Model) src);
        }

    private JsonElement getJsonElement(Model src) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getLongId());
        jsonObject.add("attributes", getJsonAttributes(src));
        return jsonObject;
    }

    private JsonObject getJsonAttributes(Model src) {
        final JsonObject jsonAttributes = new JsonObject();
        for(String f : getAttributeNames()) {
            if(src.get(f) != null) {
                jsonAttributes.addProperty(f, src.get(f).toString());
            }
        }
        return jsonAttributes;
    }

    protected abstract Set<String> getAttributeNames();
}
