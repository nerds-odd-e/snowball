package com.odde.snowball.model.practice;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
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

    private final User user1 = new User().save();
    private final User user2 = new User().save();

    @Before
    public void setUp() {
        createPractice();
    }

    private void createPractice() {
        Collection<ObjectId> categories = new ArrayList<>();
        List<Integer> cycle = Arrays.asList(1, 3, 7);

        Practice practice = new Practice(user1.getId(), 1, categories, cycle);
        practice.save();
    }

    @Test
    public void testFetchPracticeByUserId() {
        Practice practice = Practice.fetchPracticeByUser(user1);
        assertNotNull(practice);
    }

    @Test
    public void testGeneratePractice() {
        Practice.generatePractice(user2);

        Practice practice = Practice.fetchPracticeByUser(user2);
        assertNotNull(practice);
    }

    @Test
    public void testMultipleGeneratePracticeCreateOnlyOneUniquePractice() {
        Practice.generatePractice(user1);

        List<Practice> practices = repo(Practice.class).findBy("userId", user1.getId());
        assertEquals(1, practices.size());
    }

}
