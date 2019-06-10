package com.odde.massivemailer.model;

import com.odde.massivemailer.model.base.Entity;
import com.odde.massivemailer.model.base.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SentMailVisit extends Entity<SentMailVisit> {
    private String emailAddress;
    private int readCount;
    private ObjectId sentMailId;

    public String toJSON() {
        return "{\"email\": \"" + getEmailAddress() + "\", \"open_count\": " + getReadCount() + "}";
    }

    public void updateViewCount() {
        setReadCount(getReadCount() + 1);
        saveIt();
    }

    @Override
    public void onBeforeSave() {
    }

}
