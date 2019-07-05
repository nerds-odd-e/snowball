package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.enumeration.TestType;
import com.odde.snowball.model.onlinetest.*;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    private Category scrum = Category.create("Scrum");
    private Category tech = Category.create("Tech");
    private Category team = Category.create("Team");

    @Before
    public void setUpMockService() {
        controller = new QuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    private Question createQuestionWithOptions(Category category) {
        return createQuestionWithOptions(category.getId());
    }

    private Question createQuestionWithOptions(ObjectId categoryId) {
        Question question = new Question("What is Scrum?", "Scrum is a coding practice.", categoryId, false, false).save();
        ObjectId id = question.getId();
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

        String optionId = getFirstOptionId(question);
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

        String optionId = getFirstOptionId(question);
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertNull(session.getAttribute("options"));
    }

    @Test
    public void doPostWithMessageOnNotCurrentQuestionPage() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        String optionId = getFirstOptionId(question);
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

        String optionId = getFirstOptionId(question);
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

        List<ObjectId> optionId = question.correctOptions();
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
        question = createQuestionWithOptions(scrum);
        String optionId = getFirstOptionId(question);
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

        String wrongOptionId = getFirstOptionId(question);
        List<ObjectId> correctOptionId = question.correctOptions();

        final String[] answeredOption = new String[2];
        answeredOption[0] = correctOptionId.get(0).toString();
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
        List<ObjectId> optionId = question.correctOptions();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionId.get(0).toString());
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
        List<ObjectId> optionId = question.correctOptions();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionId.get(0).toString());
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
        List<ObjectId> optionIds = question.correctOptions();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionIds.get(0).toString());
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
        List<ObjectId> optionIds = question.correctOptions();
        onlineTest = new OnlineTest(2);

        request.addParameter("optionId", optionIds.get(0).toString());
        request.addParameter("lastDoneQuestionId", "0");
        request.getSession().setAttribute("onlineTest", onlineTest);

        Question currentQuestion = onlineTest.getCurrentQuestion();
        controller.doPost(request, response);
        HttpSession session = request.getSession();

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");


        assertEquals(optionIds.get(0), onlineTest.answers.get(0).getSelectedOptionIds().get(0));
    }

    public static String getFirstOptionId(Question question) {
        Collection<QuestionOption> options = question.options();
        return options.stream().findFirst().get().getStringId();
    }


}
