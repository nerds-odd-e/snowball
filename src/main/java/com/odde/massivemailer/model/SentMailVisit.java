package com.odde.massivemailer.model;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentMailVisit extends Entity {
    private String emailAddress;
    private int readCount;
    private ObjectId sentMailId;

    public static Repository<SentMailVisit> repository() {
        return new Repository<>(SentMailVisit.class, "sent_mail_visits");
    }

    public String toJSON() {
        return "{\"email\": \"" + getEmailAddress() + "\", \"open_count\": " + getReadCount() + "}";
    }

    public void updateViewCount() {
        setReadCount(getReadCount() + 1);
        saveIt();
    }

    public SentMailVisit saveIt() {
        repository().save(this);
        return this;
    }

    @Override
    public boolean onBeforeSave() {

        return true;
    }

}
