package com.odde.snowball.model.practice;

import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.onlinetest.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.odde.snowball.model.base.Repository.repo;

@NoArgsConstructor
@Setter
@Getter
public class Record extends Entity<Record> {
    private ObjectId userId;
    private ObjectId questionId;
    private LocalDate lastUpdated;
    private int cycleState;

    public Record(ObjectId userId, ObjectId questionId, LocalDate lastUpdated, int cycleState) {
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

    public static void recordQuestionForUser(ObjectId userId, ObjectId questionId) {
        List<Record> records = repo(Record.class).find(and(eq("userId", userId), eq("questionId", questionId)));
        if(records.size() == 0){
            Record record = new Record(userId, questionId, LocalDate.now(), 1);
            record.save();
        } else {
            Record record = records.get(0);
            record.setLastUpdated(LocalDate.now());
            record.setCycleState(record.getCycleState()+1);
            record.save();
        }
    }
}
