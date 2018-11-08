package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.controller.onlinetest.AddQuestionController;
import com.odde.massivemailer.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(TestWithDB.class)
public class AddQuestionControllerTest {
    private AddQuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new AddQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void doPostAddQuestion() throws Exception {
        request.setParameter("description", "aaaaaaaaaaaaaaaa");

        controller.doPost(request, response);
        String description = request.getParameter("description");
        assertEquals("/onlinetest/question_list.jsp", response.getRedirectedUrl());

        Question question = Question.findFirst("");
        assertEquals(description, question.getDescription());
    }

    @Test
    public void doPostAddQuestionAnotherParameter() throws Exception {
        request.setParameter("description", "bbbbbbbbbbbbbbb");

        controller.doPost(request, response);
        String description = request.getParameter("description");
        assertEquals("/onlinetest/question_list.jsp", response.getRedirectedUrl());

        Question question = Question.findFirst("");
        assertEquals(description, question.getDescription());
    }
}
