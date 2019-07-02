package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.controller.PracticeController;
import com.odde.snowball.model.onlinetest.*;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(TestWithDB.class)
public class PracticeControllerTest {
    private PracticeController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Question question;
    private OnlineTest onlineTest;
    private Category scrum = Category.create("Scrum");
    private Category tech = Category.create("Tech");
    private Category team = Category.create("Team");

    @Before
    public void setUpMockService() {
        controller = new PracticeController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }
    @Test
    public void redirectToAddQuestionPageIfNoQuestionsInOnlineTest() throws Exception {
        controller.doGet(request,response);
        assertEquals("/practice/completed_practice.jsp", response.getRedirectedUrl());
    }
    @Test
    public void redirect_to_question_jsp()
            throws Exception {
        mockQuestion();
        controller.doGet(request, response);
        assertEquals("/onlinetest/question.jsp", response.getRedirectedUrl());
    }

    private void mockQuestion() {
        Category cat = Category.create("Retro");
        IntStream.range(0, 1).forEach(index -> new Question("desc" + index, "adv" + index, cat.getId(), false, false).save());
    }

}
