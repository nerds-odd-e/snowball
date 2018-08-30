package com.odde.massivemailer.controller;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class QuestionControllerTest {
    private QuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void showQuestionPage() throws Exception {
        controller.doGet(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postCorrect() throws Exception {

        String optionId = "5";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");

        controller.doPost(request,response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {
        String optionId = "2";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");

        controller.doPost(request, response);

        String correctOption = (String) request.getAttribute("correctOption");
        assertEquals("5", correctOption);

        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals("2", selectedOption);
    }
}
