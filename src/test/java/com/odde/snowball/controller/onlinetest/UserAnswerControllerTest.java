package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.*;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;
import org.bson.types.ObjectId;
import org.junit.Assert;
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
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

import static com.odde.snowball.model.base.Repository.repo;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(TestWithDB.class)
public class UserAnswerControllerTest {
    private UserAnswerController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Question question;
    private OnlineTest onlineTest;
    private Category scrum = Category.create("Scrum");
    private User currentUser = new User().save();

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Before
    public void setUpMockService() {
        controller = new UserAnswerController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("currentUser", currentUser);
    }

    private Question createQuestionWithOptions(Category category) {
        return createQuestionWithOptions(category.getId());
    }

    private Question createQuestionWithOptions(ObjectId categoryId) {
        Question question = new Question("What is Scrum?", "Scrum is a coding practice.", categoryId, false, false)
                .withOption("desc1", false)
                .withOption("desc2", false)
                .withOption("desc3", false)
                .withOption("desc4", false)
                .withOption("desc5", true)
                .save();

        return question;
    }

    @Test
    public void postSaveCorrectAnswerStatus() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = OnlineQuiz.createOnlineQuiz(1);
        request.getSession().setAttribute("onlineTest", onlineTest);

        List<String> optionId = question.correctOptions();
        request.addParameter("optionId", optionId.get(0));
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        controller.doPost(request, response);

        List<UserAnswer> userAnswers = repo(UserAnswer.class).findAll();
        Assert.assertEquals(1, userAnswers.size());
    }

    @Test
    public void postIncorrect() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = OnlineQuiz.createOnlineQuiz(1);
        request.getSession().setAttribute("onlineTest", onlineTest);

        String optionId = getFirstOptionId(question);
        request.addParameter("optionId", optionId);
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        controller.doPost(request, response);
        ArrayList<String> selectedOption = (ArrayList<String>) request.getAttribute("selectedOption");
        assertEquals(optionId, selectedOption.get(0));
        assertThat((Question) request.getAttribute("currentQuestion")).isNotNull();
        assertThat(response.getForwardedUrl()).isEqualTo("/onlinetest/advice.jsp");
    }

    @Test
    public void doPostWithMessageOnNotCurrentQuestionPage() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        String optionId = getFirstOptionId(question);
        onlineTest = OnlineQuiz.createOnlineQuiz(2);
        request.addParameter("optionId", optionId);
        request.addParameter("currentQuestionId", new ObjectId().toString());
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        assertEquals("You answered previous question twice", session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithoutMessageOnCurrentQuestionPage() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        String optionId = getFirstOptionId(question);
        onlineTest = OnlineQuiz.createOnlineQuiz(2);
        request.addParameter("optionId", optionId);
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();

        assertEquals(null, session.getAttribute("alertMsg"));
    }

    private String getCurrentQuestionId() {
        return onlineTest.getCurrentQuestion().stringId();
    }

    @Test
    public void doPostWithIncrementCorrectCountOnCorrectAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);

        List<String> optionId = question.correctOptions();
        onlineTest = OnlineQuiz.createOnlineQuiz(2);

        request.addParameter("optionId", optionId.get(0));
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(1, onlineTest.testResult().getCorrectAnswerCount());
    }

    @Test
    public void doPostWithNotIncrementCorrectCountOnIncorrectAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        String optionId = getFirstOptionId(question);
        onlineTest = OnlineQuiz.createOnlineQuiz(2);
        request.addParameter("optionId", optionId);
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(0, onlineTest.testResult().getCorrectAnswerCount());
    }

    @Test
    public void doPostWithNoSelectedOptions() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = OnlineQuiz.createOnlineQuiz(2);
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        request.getSession().setAttribute("onlineTest", onlineTest);

        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertEquals(0, onlineTest.getNumberOfAnsweredQuestions());
        assertEquals("You haven't selected any option.", session.getAttribute("alertMsg"));
    }

    @Test
    public void doPostWithOneCorrectOptionAndOneIncorrectOption() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = spy(OnlineQuiz.createOnlineQuiz(1));

        request.addParameter("currentQuestionId", getCurrentQuestionId());
        request.getSession().setAttribute("onlineTest", onlineTest);

        String wrongOptionId = getFirstOptionId(question);
        List<String> correctOptionId = question.correctOptions();

        final String[] answeredOption = new String[2];
        answeredOption[0] = correctOptionId.get(0);
        answeredOption[1] = wrongOptionId;

        request.addParameter("optionId", answeredOption);
        controller.doPost(request, response);
        HttpSession session = request.getSession();
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        assertThat(onlineTest.testResult().getCorrectAnswerCount()).isEqualTo(0);
    }

    @Test
    public void doPostWithIncorrectOption() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = spy(OnlineQuiz.createOnlineQuiz(1));
        request.getSession().setAttribute("onlineTest", onlineTest);

        List<String> optionId = question.inCorrectOptions();
        request.addParameter("optionId", optionId.get(0));
        request.addParameter("currentQuestionId", getCurrentQuestionId());
        controller.doPost(request, response);

        List<AnswerStatus> answerList = repo(AnswerStatus.class).findBy("userId", currentUser.stringId());

        Assert.assertEquals(0, answerList.size());
    }

    @Test
    public void postPracticeSaveUserAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = spy(OnlinePractice.createOnlinePractice(currentUser,1));
        request.getSession().setAttribute("onlineTest", onlineTest);
        List<String> optionId = question.correctOptions();
        request.addParameter("optionId", optionId.get(0));
        request.addParameter("currentQuestionId", getCurrentQuestionId());

        request.getSession().setAttribute("isPractice","true");
        controller.doPost(request, response);
        assertEquals("true",request.getSession().getAttribute("isPractice"));

        List<UserAnswer> userAnswers = repo(UserAnswer.class).findAll();
        Assert.assertEquals(1, userAnswers.size());

    }

    @Test
    public void postPracticeNotSaveUserAnswer() throws ServletException, IOException {
        question = createQuestionWithOptions(scrum);
        onlineTest = spy(OnlinePractice.createOnlinePractice(currentUser,1));
        request.getSession().setAttribute("onlineTest", onlineTest);
        List<String> optionId = question.inCorrectOptions();
        request.addParameter("optionId", optionId.get(0));
        request.addParameter("currentQuestionId", getCurrentQuestionId());

        request.getSession().setAttribute("isPractice","true");
        controller.doPost(request, response);
        assertEquals("true",request.getSession().getAttribute("isPractice"));

        List<UserAnswer> userAnswers = repo(UserAnswer.class).findAll();
        Assert.assertEquals(0, userAnswers.size());

    }



    public static String getFirstOptionId(Question question) {
        Collection<QuestionOption> options = question.getOptions();
        return options.stream().findFirst().get().stringId();
    }
}
