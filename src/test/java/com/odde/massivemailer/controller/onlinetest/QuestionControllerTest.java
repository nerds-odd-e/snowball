package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

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

    private Question createQuestionWithOptions() {
        Question question = Question.createIt("description", "What is Scrum?", "advice", "Scrum is a coding practice.");
        Long id = (Long) question.getId();
        AnswerOption.createIt("description", "desc1", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc2", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc3", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc4", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc5", "question_id", id, "is_correct", 1);

        return question;
    }

    @Ignore
    @Test
    public void postIncorrect() throws ServletException, IOException {
        String optionId = question.getFirstOptionId().toString();
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");
        controller.doPost(request, response);
        Object selectOption = request.getAttribute("selectedOption");
        String selectedOption = "";

        if (selectOption instanceof String) {
            selectedOption = (String) selectOption;
        }
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
        assertEquals("You answered previous question twice", session.getAttribute("alertMsg"));
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

        assertEquals("", session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithIncrementCorrectCountOnCorrectAnswer() throws ServletException, IOException {
        List<Long> optionId = question.getCorrectOption();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionId.get(0).toString());
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1, onlineTest.getCorrectAnswerCount());
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

    @Test
    public void doPostWithNoSelectedOptions() throws ServletException, IOException {
        onlineTest = new OnlineTest(2);
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(0, onlineTest.getNumberOfAnsweredQuestions());
    }

    @Test
    public void doPostWithOneCorrectOptionAndOneIncorrectOption() throws ServletException, IOException {
        onlineTest = spy(new OnlineTest(1));

        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        Long wrongOptionId = question.getFirstOptionId();
        List<Long> correctOptionId = question.getCorrectOption();

        final String[] answeredOption = new String[2];
        answeredOption[0] = correctOptionId.get(0).toString();
        answeredOption[1] = wrongOptionId.toString();

        request.addParameter("optionId", answeredOption);
        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");

        verify(onlineTest, times(0)).incrementCorrectAnswerCount();
    }
}
