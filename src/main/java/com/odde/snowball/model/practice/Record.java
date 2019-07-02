package com.odde.snowball.model.practice;

import com.odde.snowball.model.base.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.odde.snowball.model.base.Repository.repo;

@NoArgsConstructor
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

    public static Collection<Record> fetchRecordsByUserId(ObjectId userId) {
        if (null == userId) {
            return new ArrayList<>();
        }

        return repo(Record.class).findBy("userId", userId);
    }
}
