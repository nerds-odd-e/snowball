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
    public void showQuestionPage() throws Exception {
        controller.doGet(request,response);
        assertEquals("/onlinetest/question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void doPostAnswerAdditionalQuestionAnswerCountIncreases() throws Exception {
        when(onlineTest.hasNextQuestion()).thenReturn(true);

        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        request.addParameter("optionId", optionId);

        controller.doPost(request,response);
        assertEquals("/onlinetest/question.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postCorrectOptionInTheLastQuestion() throws Exception {

        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        request.addParameter("optionId", optionId);

        controller.doPost(request,response);
        assertEquals("/onlinetest/end_of_test.jsp", response.getRedirectedUrl());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {

        Long optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId.toString());
        controller.doPost(request, response);
        String selectedOption = (String) request.getAttribute("selectedOption");
        assertEquals(optionId, selectedOption);
    }

    @Test
    public void firstDoPost() throws IOException {
        launchQuestionController.doGet(request, response);
        HttpSession session = request.getSession();
        assertEquals(0, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithCorrectAnsweredOption() throws ServletException, IOException {
        when(onlineTest.hasNextQuestion()).thenReturn(true);

        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;

        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("/onlinetest/question.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
        assertEquals(4, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostAtLastQuestionWithCorrectAnsweredOption() throws ServletException, IOException {
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;
        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("/onlinetest/end_of_test.jsp", response.getRedirectedUrl());
        HttpSession session = request.getSession();
        assertEquals(4, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithIncorrectAnsweredOption() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId.toString());
        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("/onlinetest/advice.jsp", response.getForwardedUrl());
        HttpSession session = request.getSession();
        assertEquals(3, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostAtLastQuestionWithIncorrectAnsweredOption() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId.toString());

        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        assertEquals("/onlinetest/advice.jsp", response.getForwardedUrl());
        HttpSession session = request.getSession();
        assertEquals(3, (int) session.getAttribute("correctlyAnsweredCount"));
    }

    @Test
    public void doPostWithNoOptionsInDatabase() throws ServletException, IOException {
        Long optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId.toString());

        request.getSession().setAttribute("correctlyAnsweredCount", 3);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }

    @Test
    public void doPostAtLastQuestionWithTestIdInSession() throws ServletException, IOException {
        // given
        Collection<AnswerOption> options = question.getOptions();
        Optional<AnswerOption> correctId = options.stream().filter(AnswerOption::isCorrect).findFirst();
        String optionId = correctId.isPresent() ? correctId.get().getId().toString() : StringUtils.EMPTY;
        request.addParameter("optionId", optionId);
        request.getSession().setAttribute("correctlyAnsweredCount", 3);
        // when
        controller.doPost(request, response);
        Integer totalScore = (Integer)request.getAttribute("totalScore");
        // then
        assertEquals(new Integer(4), totalScore);

    }

}
