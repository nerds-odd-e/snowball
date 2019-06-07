package com.odde.massivemailer.serialiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odde.massivemailer.model.*;
import org.bson.types.ObjectId;

public class AppGson {
    static public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerialiser())
                .create();
    }
}
