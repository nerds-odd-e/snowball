package com.odde.massivemailer.controller;

import com.google.common.collect.Lists;
import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import cucumber.api.java.gl.E;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QuestionControllerTest {
    private QuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("answeredCount", 3);
    }

    @Test
    public void showQuestionPage() throws Exception {
        controller.doGet(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void setSessionInFirstRequest() throws Exception {
        controller.doGet(request, response);
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("onlineTest");
        assertThat(obj, instanceOf(OnlineTest.class));
        OnlineTest onlineTest = (OnlineTest) obj;
        List<Question> questions = onlineTest.getQuestions();
        assertTrue(questions.size() > 0);
        for (Question question : questions) {
            assertTrue(question.getOptions().size() > 1);
        }
    }

    @Test
    public void postCorrect() throws Exception {

        String optionId = "5";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");
        request.getSession().setAttribute("answeredCount", 3);

        controller.doPost(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
        assertEquals(4, (int) session.getAttribute("answeredCount"));
    }

    @Test
    public void postCorrectOptionInTheLastQuestion() throws Exception {

        String optionId = "5";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");
        request.getSession().setAttribute("answeredCount", 10);

        controller.doPost(request,response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {
        String optionId = "2";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");
        request.addParameter("from", "question");

        controller.doPost(request, response);

        String correctOption = (String) request.getAttribute("correctOption");
        assertEquals("5", correctOption);

        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals("2", selectedOption);

        String[] options = (String[]) request.getAttribute("options");
        String[] expectedOptions = {
                "Scrum is Rugby",
                "Scrum is Baseball",
                "Scrum is Soccer",
                "Scrum is Sumo",
                "None of the above"
        };
        for (int i = 0; i < options.length; ++i) {
            assertEquals(expectedOptions[i], options[i]);
        }
    }

    @Test
    public void checkFromAdvicePageToQuestionPage() throws Exception {

        request.addParameter("from", "advice");

        controller.doPost(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void checkFromAdvicePageToEndOfTestPage() throws Exception {

        request.addParameter("from", "advice");
        request.getSession().setAttribute("answeredCount", 10);

        controller.doPost(request, response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

}
