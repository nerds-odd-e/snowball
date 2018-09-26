package com.odde.massivemailer.controller;

import com.google.common.collect.Lists;
import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Option;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.service.OnlineTestService;
import cucumber.api.java.gl.E;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

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

        String optionId = "2";

        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(1L, "Yes", false));
        options.add(new Option(2L, "No", true));
        List<Question> questions = new ArrayList<>();
        Question question = new Question("Is same of feature and story?", options, "");
        Question question2 = new Question("", options, "");
        questions.add(question);
        questions.add(question2);
        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);
        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");

        controller.doPost(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postCorrectOptionInTheLastQuestion() throws Exception {

        String optionId = "2";

        request.addParameter("optionId", optionId);
        ArrayList<Option> options = new ArrayList<>();
        options.add(new Option(1L, "Yes", false));
        options.add(new Option(2L, "No", true));
        List<Question> questions = new ArrayList<>();
        Question question = new Question("Is same of feature and story?", options, "");
        questions.add(question);
        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request,response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {
        String optionId = "1";

        ArrayList<Question> questions = new ArrayList<>();
        Question question1 = new Question("", null, null, null);
        Question question2 = new Question("", null, null, null);
        questions.add(question1);
        questions.add(question2);

        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");
        request.addParameter("from", "question");

        controller.doPost(request, response);

        String correctOption = (String) request.getAttribute("correctOption");
        assertEquals("2", correctOption);

        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals("1", selectedOption);

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

        ArrayList<Question> questions = new ArrayList<>();
        Question question1 = new Question("", null, null, null);
        Question question2 = new Question("", null, null, null);
        questions.add(question1);
        questions.add(question2);

        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);
        request.addParameter("from", "advice");

        controller.doPost(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void checkFromAdvicePageToEndOfTestPage() throws Exception {

        List<Question> questions = new ArrayList<>();
        ArrayList<Option> options = new ArrayList<>();
        questions.add(new Question("", options, null, 1L));
        OnlineTest onlineTest = new OnlineTestService().generateFromQuestions(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);
        request.addParameter("from", "advice");
        request.getSession().setAttribute("answeredCount", 10);

        controller.doPost(request, response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void shouldShowProgressStateOnFirstPage() throws Exception {
        ArrayList<Question> questions = new ArrayList<>();
        Question question1 = new Question("", null, null, null);
        Question question2 = new Question("", null, null, null);
        questions.add(question1);
        questions.add(question2);

        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);
        controller.doGet(request,response);

        String progressState = (String) request.getSession().getAttribute("progressState");
        assertEquals("1/2", progressState);
    }

    @Test
    public void shouldShowProgressStateLastPage() throws Exception {
        ArrayList<Question> questions = new ArrayList<>();
        Question question1 = new Question("", null, null, 1L);
        Question question2 = new Question("", null, null, null);
        questions.add(question1);
        questions.add(question2);

        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);
        controller.doGet(request,response);

        String progressState = (String) request.getSession().getAttribute("progressState");
        assertEquals("2/2", progressState);
    }

}
