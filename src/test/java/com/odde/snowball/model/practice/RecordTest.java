package com.odde.snowball.model.practice;

import com.odde.TestWithDB;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class RecordTest {
    private final ObjectId userId1 = new ObjectId();
    private final ObjectId userId2 = new ObjectId();

    private final ObjectId questionId1 = new ObjectId();
    private final ObjectId questionId2 = new ObjectId();

    @Before
    public void setUp() {
        createRecord();
    }

    private void createRecord() {
        Record record = new Record(userId1, questionId1, LocalDate.now(), 0);
        record.save();
    }

    @Test
    public void testFetchRecordsByUserId() {
        Collection<Record> records = Record.fetchRecordsByUserId(userId1);
        assertNotNull(records);
        assertEquals(questionId1, records.iterator().next().getQuestionId());
    }

    @Test
    public void testSaveRecordQuestionForFirstTime() {
        Record.recordQuestionForUser(userId2, questionId2);
        assertEquals(1, Record.fetchRecordsByUserId(userId2).size());
    }

    @Test
    public void testSaveRecordWithUpdatedCycleState() {
        Record.recordQuestionForUser(userId1, questionId1);
        Collection<Record> records = Record.fetchRecordsByUserId(userId1);
        assertEquals(1, records.iterator().next().getCycleState());
    }

}
