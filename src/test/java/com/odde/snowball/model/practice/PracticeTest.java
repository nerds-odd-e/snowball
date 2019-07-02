package com.odde.snowball.model.practice;

import com.odde.TestWithDB;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(TestWithDB.class)
public class PracticeTest {

    private final ObjectId userId = new ObjectId();

    @Before
    public void setUp() {
        createPractice();
    }

    private void createPractice() {
        Collection<ObjectId> categories = new ArrayList<>();
        List<Integer> cycle = Arrays.asList(1, 3, 7);

        Practice practice = new Practice(userId, 1, categories, cycle);
        practice.save();
    }

    @Test
    public void testFetchPracticeByUserId() {
        Practice practice = Practice.fetchPracticeByUserId(userId);
        assertNotNull(practice);
    }

}
