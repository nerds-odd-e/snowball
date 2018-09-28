package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
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
            assertTrue(question.getQuestionOptions().size() > 1);
        }
    }

    @Test
    public void postCorrect() throws Exception {

        String optionId = "1";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");
        setOnlineTestToSession(0, 2);

        controller.doPost(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1, onlineTest.countAnsweredQuestions());
    }



    @Test
    public void postIncorrect() throws ServletException, IOException {
        String optionId = "2";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");
        request.addParameter("from", "question");
        setOnlineTestToSession(0, 2);

        controller.doPost(request, response);

        String correctOption = (String) request.getAttribute("correctOption");
        assertEquals("1", correctOption);

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
        setOnlineTestToSession(1,1);

        controller.doPost(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void checkFromAdvicePageToEndOfTestPage() throws Exception {

        request.addParameter("from", "advice");

        setOnlineTestToSession(2, 0);

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
        setOnlineTestToSession(1, 1);
        controller.doGet(request,response);

        String progressState = (String) request.getSession().getAttribute("progressState");
        assertEquals("2/2", progressState);
    }

    @Test
    public void postCorrectOptionInTheLastQuestion() throws Exception {

        String optionId = "1";

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", "1");


        setOnlineTestToSession(1, 1);

        controller.doPost(request,response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    private void setOnlineTestToSession(int answeredQuestionCount, int unansweredQuestionCount) {
        ArrayList<Question> questions = new ArrayList<>();
        for (long i = 0; i < answeredQuestionCount; i++) {
            Question question = new Question("", null, null, 1L);
            question.setId(i);
            questions.add(question);
        }
        for (long i = 0; i < unansweredQuestionCount; i++) {
            Question question = new Question("", null, null, null);
            question.setId(i + answeredQuestionCount);
            questions.add(question);
        }

        OnlineTest onlineTest = new OnlineTest(questions);
        request.getSession().setAttribute("onlineTest", onlineTest);
    }
}
