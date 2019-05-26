package com.odde.massivemailer.serialiser;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdSerialiser extends TypeAdapter<ObjectId> {
    @Override
    public void write(JsonWriter out, ObjectId value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public ObjectId read(JsonReader in) throws IOException {
        return null;
    }
}
