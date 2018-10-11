package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.AnswerOption;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.QuestionResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

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
        prepareQuestionResponse();
    }

    private void prepareQuestionResponse() {
        for(int i=0;i<10;i++) {
            QuestionResponse qr = new QuestionResponse().createIt("testid-01", "1", i<6);
            qr.saveIt();
        }
    }


    @Test
    @Ignore
    public void showTestResultPage() throws IOException {
        controller.doGet(request, response);
        Assert.assertEquals("test_result.jsp", response.getRedirectedUrl());
        Assert.assertTrue(response.getContentAsString().contains("60%"));
    }

    private Question createQuestionWithOptions(){
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        Long id = (Long)question.getId();
        AnswerOption.createIt("description", "desc1", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc2", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc3", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc4", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc5", "question_id", id, "is_correct", 1);

        return question;
    }

}
