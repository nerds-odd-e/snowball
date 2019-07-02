package com.odde.snowball.model.practice;

import com.odde.TestWithDB;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class RecordTest {
    private final ObjectId userId = new ObjectId();
    private final ObjectId questionId = new ObjectId();

    @Before
    public void setUp() {
        createRecord();
    }

    private void createRecord() {
        Record record = new Record(userId, questionId, new Date(), 0);
        record.save();
    }

    @Test
    public void testFetchRecordsByUserId() {
        Collection<Record> records = Record.fetchRecordsByUserId(userId);
        assertNotNull(records);
        assertEquals(questionId, records.iterator().next().getQuestionId());
    }
}
