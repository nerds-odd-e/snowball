package com.odde.snowball.model.practice;

import com.odde.snowball.model.base.Entity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import java.util.Date;

@Setter
@Getter
public class Record extends Entity<Record> {
    private ObjectId userId;
    private ObjectId questionId;
    private Date lastUpdated;
    private int cycleState;

    public Record(ObjectId userId, ObjectId questionId, Date lastUpdated, int cycleState) {
        this.userId = userId;
        this.questionId = questionId;
        this.lastUpdated = lastUpdated;
        this.cycleState = cycleState;
    }
}
