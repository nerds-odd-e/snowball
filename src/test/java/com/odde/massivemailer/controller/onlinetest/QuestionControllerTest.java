package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.*;
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
    private Category scrum = Category.createIt("name", "Scrum");
    private Category tech = Category.createIt("name", "Tech");
    private Category team = Category.createIt("name", "Team");

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    private Question createQuestionWithOptions(Category category) {
        return createQuestionWithOptions(category.getStringId());
    }

    private Question createQuestionWithOptions(String categoryId) {
        Question question = Question.createIt("description", "What is Scrum?", "advice", "Scrum is a coding practice.", "category", categoryId);
        String id = question.getStringId();
        QuestionOption.createIt(id, "desc1", false);
        QuestionOption.createIt(id, "desc2", false);
        QuestionOption.createIt(id, "desc3", false);
        QuestionOption.createIt(id, "desc4", false);
        QuestionOption.createIt(id, "desc5", true);

        return question;
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = new OnlineTest(1);
        request.getSession().setAttribute("onlineTest", onlineTest);

        String optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");
        controller.doPost(request, response);
        ArrayList<String> selectedOption =  (ArrayList<String>) request.getAttribute("selectedOption");
        assertEquals(optionId, selectedOption.get(0));
    }

    @Test
    public void doPostWithNoOptionsInDatabase() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = new OnlineTest(1);
        request.getSession().setAttribute("onlineTest", onlineTest);

        String optionId = question.getFirstOptionId();
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }

    @Test
    public void doPostWithMessageOnNotCurrentQuestionPage() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        String optionId = question.getFirstOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "1");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertEquals("You answered previous question twice", session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithoutMessageOnCurrentQuestionPage() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        String optionId = question.getFirstOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();

        assertEquals("", session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithIncrementCorrectCountOnCorrectAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        List<String> optionId = question.getCorrectOption();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionId.get(0));
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1, onlineTest.getCorrectAnswerCount());
    }

    @Test
    public void doPostWithNotIncrementCorrectCountOnIncorrectAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        String optionId = question.getFirstOptionId();
        onlineTest = new OnlineTest(2);
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(0, onlineTest.getCorrectAnswerCount());
    }

    @Test
    public void doPostWithNoSelectedOptions() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
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
        question = createQuestionWithOptions(scrum);
        onlineTest = spy(new OnlineTest(1));

        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        String wrongOptionId = question.getFirstOptionId();
        List<String> correctOptionId = question.getCorrectOption();

        final String[] answeredOption = new String[2];
        answeredOption[0] = correctOptionId.get(0);
        answeredOption[1] = wrongOptionId;

        request.addParameter("optionId", answeredOption);
        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");

        verify(onlineTest, times(0)).incrementCorrectAnswerCount();
    }

    @Test
    public void doPostWithIncrementScrumCategoryCorrectCountOnCorrectAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(tech);
        List<String> optionId = question.getCorrectOption();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionId.get(0));
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);

        HttpSession session = request.getSession();

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1, onlineTest.getCategoryCorrectAnswerCount(tech.getStringId()));
    }

    @Test
    public void doPostWithIncrementScrumCategoryCorrectCountOnCorrectAnswer2() throws ServletException, IOException {
        question = createQuestionWithOptions(tech);
        List<String> optionId = question.getCorrectOption();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionId.get(0));
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);

        HttpSession session = request.getSession();

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");

        List<CategoryTestResult> categoryQuestionList = onlineTest.categoryTestResults;
        assertEquals(1, categoryQuestionList.get(0).questionCount);
    }

    @Test
    public void doPostWithIncrementTechCategoryCorrectCountOnCorrectAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(team);
        List<String> optionIds = question.getCorrectOption();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionIds.get(0));
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);

        HttpSession session = request.getSession();

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1, onlineTest.getCategoryCorrectAnswerCount(team.getStringId()));
    }

    @Test
    public void answerCurrentQuestionInParameter() throws ServletException, IOException{
        question = createQuestionWithOptions(tech);
        List<String> optionIds = question.getCorrectOption();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionIds.get(0));
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        Question currentQuestion = onlineTest.getCurrentQuestion();
        controller.doPost(request, response);
        HttpSession session = request.getSession();

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");


        assertEquals(optionIds.get(0), onlineTest.answers.get(0).getSelectedOptionIds().get(0));
    }


}
