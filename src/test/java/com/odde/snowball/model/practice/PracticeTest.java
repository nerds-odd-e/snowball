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

import static com.odde.snowball.model.base.Repository.repo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(TestWithDB.class)
public class PracticeTest {

    private final ObjectId userId1 = new ObjectId();
    private final ObjectId userId2 = new ObjectId();

    @Before
    public void setUp() {
        createPractice();
    }

    private void createPractice() {
        Collection<ObjectId> categories = new ArrayList<>();
        List<Integer> cycle = Arrays.asList(1, 3, 7);

        Practice practice = new Practice(userId1, 1, categories, cycle);
        practice.save();
    }

    @Test
    public void testFetchPracticeByUserId() {
        Practice practice = Practice.fetchPracticeByUserId(userId1);
        assertNotNull(practice);
    }

    @Test
    public void testGeneratePractice() {
        Practice.generatePractice(userId2);

        Practice practice = Practice.fetchPracticeByUserId(userId2);
        assertNotNull(practice);
    }

    @Test
    public void testMultipleGeneratePracticeCreateOnlyOneUniquePractice() {
        Practice.generatePractice(userId1);

        List<Practice> practices = repo(Practice.class).findBy("userId", userId1);
        assertEquals(1, practices.size());
    }

}
