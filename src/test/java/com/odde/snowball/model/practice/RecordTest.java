package com.odde.snowball.model.practice;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Question;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class RecordTest {
    private final User user = new User("test@test.com");
    private final Question question = new Question("test", "advice", new ObjectId(), false, true);

    @Before
    public void setUp(){
        user.save();
        question.save();
    }

    private void createRecord() {
        Record record = new Record(user.getId(), question.getId(), LocalDate.now(), 0);
        record.save();
    }

    @Test
    public void testFetchRecordsByUserId() {
        createRecord();
        Collection<Record> records = Record.fetchRecordsByUserId(user.getId());
        assertEquals(question.getId(), records.iterator().next().getQuestionId());
    }

    @Test
    public void testSaveRecordQuestionForFirstTime() {
        question.recordQuestionForUser(user.getId(), LocalDate.now());
        assertEquals(1, Record.fetchRecordsByUserId(user.getId()).size());
    }

    @Test
    public void testSaveRecordWithUpdatedCycleState() {
        createRecord();
        question.recordQuestionForUser(user.getId(), LocalDate.now());
        Collection<Record> records = Record.fetchRecordsByUserId(user.getId());
        assertEquals(1, records.iterator().next().getCycleState());
    }

}
