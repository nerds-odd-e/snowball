package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(TestWithDB.class)
public class QuestionControllerTest {
    private QuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Question question;
    private LaunchQuestionController launchQuestionController;
    private OnlineTest onlineTest;

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        launchQuestionController = new LaunchQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("correctlyAnsweredCount", 3);
        question = createQuestionWithOptions();
        onlineTest = mock(OnlineTest.class);
        when(onlineTest.getCurrentQuestion()).thenReturn(question);
        request.getSession().setAttribute("onlineTest", onlineTest);
    }


    private Question createQuestionWithOptions(){
        Question question = Question.createIt("description", "What is Scrum?", "advice", "Scrum is a coding practice.");
        Long id = (Long)question.getId();
        AnswerOption.createIt("description", "desc1", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc2", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc3", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc4", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc5", "question_id", id, "is_correct", 1);

        return question;
    }


    @Test
    public void postIncorrect() throws ServletException, IOException {
        String optionId = question.getFirstOptionId().toString();
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");
        controller.doPost(request, response);
        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals(optionId, selectedOption);
    }

    @Test
    public void doPostWithNoOptionsInDatabase() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId.toString());
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }
}
