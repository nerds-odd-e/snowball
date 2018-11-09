package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(TestWithDB.class)
public class QuestionControllerTest {
    private QuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Question question;
    private OnlineTest onlineTest;

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        question = createQuestionWithOptions();
        onlineTest = new OnlineTest(1);
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

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }

    @Test
    public void doPostWithMessageOnNotCurrentQuestionPage() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId.toString());
        request.addParameter("lastDoneQuestionId", "1");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertEquals("You answered previous question twice",session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithoutMessageOnCurrentQuestionPage() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId.toString());
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertEquals("",session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithIncrementCorrectCountOnCorrectAnswer() throws ServletException, IOException {
        Long optionId = question.getCorrectOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId.toString());
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1,onlineTest.getCorrectAnswerCount());
    }
    
    @Test
    public void doPostWithNotIncrementCorrectCountOnIncorrectAnswer() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId.toString());
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(0, onlineTest.getCorrectAnswerCount());
    }
}
