package com.odde.snowball.controller.practice;

import com.odde.TestWithDB;
import com.odde.snowball.model.practice.Practice;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class PracticeControllerTest {
    private final ObjectId userId = new ObjectId();

    private PracticeController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private void createPractice() {
        Collection<ObjectId> categories = new ArrayList<>();
        List<Integer> cycle = Arrays.asList(1, 3, 7);

        Practice practice = new Practice(userId, 1, categories, cycle);
        practice.save();
    }

    @Before
    public void setUpMockService() {
        controller = new PracticeController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("userId", userId);
    }

    @Test
    public void retrievePracticeIfAlreadyExists() {
        createPractice();
        Practice practice = controller.ensurePractice(userId);
        assertNotNull(practice);
        assertEquals(userId, practice.getUserId());
    }

    @Test
    public void createPracticeIfDoesNotExist() {
        Practice practice = controller.ensurePractice(userId);
        assertNotNull(practice);
    }


}
