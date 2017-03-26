package com.odde.massivemailer.serialiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import com.odde.massivemailer.model.Notification;
import com.odde.massivemailer.model.Template;

public class AppGson {
    static public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerialiser())
                .registerTypeAdapter(ContactPerson.class, new ContactPersonSerialiser())
                .registerTypeAdapter(Template.class, new TemplateSerialiser())
                .registerTypeAdapter(Notification.class, new NotificationSerialiser())
                .create();
    }
}
