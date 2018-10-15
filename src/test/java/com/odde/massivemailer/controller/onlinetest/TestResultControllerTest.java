package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.QuestionResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class TestResultControllerTest {

    private TestResultController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new TestResultController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    private void prepareQuestionResponse() {
        for(int i = 0; i< 10; i++) {
            QuestionResponse.createIt("test_id", "test-001",
                    "question_id", i,
                    "is_answer_correct", i< 6);
        }
        assertEquals(10, QuestionResponse.where("test_id = ? ","test-001" ).size());
    }


    @Test
    public void itMustForwardToTestResultPage() throws IOException, ServletException {
        prepareQuestionResponse();
        request.setParameter("quizId","test-001");
        controller.doGet(request, response);
        assertEquals("test_result.jsp", response.getForwardedUrl());
    }
}

