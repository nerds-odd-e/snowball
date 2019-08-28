package com.odde.snowball.model.onlinetest;

import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Arrays;
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
    private LocalDate nextShowDate;
    private int cycleState;
    public final static List<Integer> CYCLE = Arrays.asList(1, 3, 10, 30, 90);

    public Record(User user, Question question) {
        this.userId = user.getId();
        this.questionId = question.getId();
        this.cycleState = 0;
    }

    public static Record getOrInitializeRecord(User user, Question question) {
        Record record = repo(Record.class).findFirst(and(eq("userId", user.getId()), eq("questionId", question.getId())));
        if (record == null) {
            return new Record(user, question);
        }
        return record;
    }

    public void setNextShowDate() {
        if (this.lastUpdated != null && getCycleState() != 0 && !isOverCycle()) {
            this.nextShowDate = getLastUpdated().plusDays(CYCLE.get(getCycleState() - 1));
        }
    }


    public boolean isDue() {
        if (getCycleState() == 0) {
            return true;
        }
        if (isOverCycle()) {
            return false;
        }
        return !getLastUpdated()
                .plusDays(CYCLE.get(getCycleState() - 1))
                .isAfter(LocalDate.now());
    }

    private boolean isOverCycle() {
        return getCycleState() > CYCLE.size();
    }
}
