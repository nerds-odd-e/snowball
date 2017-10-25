package com.odde.massivemailer.serialiser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.odde.massivemailer.model.*;

public class AppGson {
    static public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventSerialiser())
                .registerTypeAdapter(Course.class, new CourseSerialiser())
                .registerTypeAdapter(ContactPerson.class, new ContactPersonSerialiser())
                .registerTypeAdapter(Template.class, new TemplateSerialiser())
                .registerTypeAdapter(Notification.class, new NotificationSerialiser())
                .registerTypeAdapter(MailLog.class, new MailLogSerialiser())
                .create();
    }
}
