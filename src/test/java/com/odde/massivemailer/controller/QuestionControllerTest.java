package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.AnswerOption;
import com.odde.massivemailer.model.Question;
import org.assertj.core.util.Lists;
import org.javalite.activejdbc.Model;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

import static com.odde.massivemailer.controller.QuestionController.MAX_QUESTION_COUNT;
import static com.odde.massivemailer.util.QuestionUtil.getCorrectOptionId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(TestWithDB.class)
public class QuestionControllerTest {
    private QuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Question question;

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("answeredCount", 3);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);
        question = createQuestionWithOptions();
    }

    public Question createQuestionWithOptions(){
        Question question = Question.createIt("description", "desc1", "advice", "adv1");
        Long id = (Long)question.getId();
        AnswerOption answerOption1 = AnswerOption.createIt("description", "desc1", "question_id", id, "is_correct", 0);
        AnswerOption answerOption2 = AnswerOption.createIt("description", "desc2", "question_id", id, "is_correct", 0);
        AnswerOption answerOption3 = AnswerOption.createIt("description", "desc3", "question_id", id, "is_correct", 0);
        AnswerOption answerOption4 = AnswerOption.createIt("description", "desc4", "question_id", id, "is_correct", 0);
        AnswerOption answerOption5 = AnswerOption.createIt("description", "desc5", "question_id", id, "is_correct", 1);

        return question;
    }

    @Test
    public void showQuestionPage() throws Exception {
        controller.doGet(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void doPostAnswerAdditionalQuestionAnswerCountIncreases() throws Exception {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = getCorrectOptionId(options);

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.getSession().setAttribute("answeredCount", 3);

        controller.doPost(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
        assertEquals(4, (int) session.getAttribute("answeredCount"));
    }

    @Test
    public void postCorrectOptionInTheLastQuestion() throws Exception {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = getCorrectOptionId(options);

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.getSession().setAttribute("answeredCount", 10);

        controller.doPost(request,response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String correctOptionId = getCorrectOptionId(options);

        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.addParameter("from", "question");

        controller.doPost(request, response);

        String correctOption = (String) request.getAttribute("correctOption");
        assertEquals(correctOptionId, correctOption);

        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals(optionId, selectedOption);

        String[] shownList = (String[])request.getAttribute("options");
        String[] expectedList ={"desc1",
                "desc2",
                "desc3",
                "desc4",
                "desc5"};
        for (int i = 0; i < shownList.length; ++i) {
            assertEquals(expectedList[i], shownList[i]);
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


    // ===========

    @Test
    public void firstDoPost() throws ServletException, IOException {
        controller.doGet(request,response);
        HttpSession session = request.getSession();
        assertEquals(0, (int) session.getAttribute("answeredCount"));
        assertEquals(0, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithCorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = getCorrectOptionId(options);

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.addParameter("from", "question");
        request.getSession().setAttribute("answeredCount", 3);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);


        controller.doPost(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
        assertEquals(4, (int) session.getAttribute("answeredCount"));
        assertEquals(4, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostAtLastQuestionWithCorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = getCorrectOptionId(options);
        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.addParameter("from", "question");
        request.getSession().setAttribute("answeredCount", 9);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
        assertEquals(MAX_QUESTION_COUNT, (int) session.getAttribute("answeredCount"));
        assertEquals(4, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithIncorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.addParameter("from", "question");
        request.getSession().setAttribute("answeredCount", 3);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("advice.jsp", response.getForwardedUrl());
        HttpSession session = request.getSession();
        assertEquals(4, (int) session.getAttribute("answeredCount"));
        assertEquals(3, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostAtLastQuestionWithIncorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.addParameter("from", "question");
        request.getSession().setAttribute("answeredCount", 9);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("advice.jsp", response.getForwardedUrl());
        HttpSession session = request.getSession();
        assertEquals(MAX_QUESTION_COUNT, (int) session.getAttribute("answeredCount"));
        assertEquals(3, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithNoOptionsInDatabase() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = AnswerOption.getForQuestion(questionId);
        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.addParameter("questionId", questionId.toString());
        request.addParameter("from", "question");
        request.getSession().setAttribute("answeredCount", 9);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }

}
