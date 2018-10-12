package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.AnswerOption;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.Quiz;
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
    private Quiz quiz;

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        launchQuestionController = new LaunchQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("correctlyAnsweredCount", 3);
        question = createQuestionWithOptions();
        quiz = mock(Quiz.class);
        when(quiz.getCurrentQuestion()).thenReturn(question);
        request.getSession().setAttribute("quiz", quiz);
    }


    public Question createQuestionWithOptions(){
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
    public void showQuestionPage() throws Exception {
        controller.doGet(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void doPostAnswerAdditionalQuestionAnswerCountIncreases() throws Exception {
        when(quiz.hasNextQuestion()).thenReturn(true);

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        request.addParameter("optionId", optionId);

        controller.doPost(request,response);
        assertEquals("question.jsp", response.getRedirectedUrl());
//        HttpSession session = request.getSession();
//        assertEquals(4, (int) session.getAttribute("answeredCount"));
    }

    @Test
    public void postCorrectOptionInTheLastQuestion() throws Exception {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        request.addParameter("optionId", optionId);

        controller.doPost(request,response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String correctOptionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);

        controller.doPost(request, response);

        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals(optionId, selectedOption);
    }

    @Test
    public void firstDoPost() throws ServletException, IOException {
        launchQuestionController.doGet(request, response);
        HttpSession session = request.getSession();
        assertEquals(0, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithCorrectAnsweredOption() throws ServletException, IOException {
        when(quiz.hasNextQuestion()).thenReturn(true);

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("question.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
//        assertEquals(4, (int) session.getAttribute("answeredCount"));
        assertEquals(4, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostAtLastQuestionWithCorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;
        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("end_of_test.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
//        assertEquals(MAX_QUESTION_COUNT, (int) session.getAttribute("answeredCount"));
        assertEquals(4, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithIncorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("advice.jsp", response.getForwardedUrl());
        HttpSession session = request.getSession();
//        assertEquals(4, (int) session.getAttribute("answeredCount"));
        assertEquals(3, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostAtLastQuestionWithIncorrectAnsweredOption() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("advice.jsp", response.getForwardedUrl());
        HttpSession session = request.getSession();
//        assertEquals(MAX_QUESTION_COUNT, (int) session.getAttribute("answeredCount"));
        assertEquals(3, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithNoOptionsInDatabase() throws ServletException, IOException {

        Long questionId = (Long) question.getId();
        Collection<AnswerOption> options = question.getOptions();
        String optionId = options.stream().findFirst().get().getId().toString();

        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }
}
