package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.base.Entity;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.odde.snowball.model.base.Repository.repo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class LaunchPracticeControllerTest {
    private LaunchPracticeController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private User user1 = new User().save();
    private User user2 = new User().save();

    @Before
    public void setUpMockService() {
        controller = new LaunchPracticeController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("currentUser", user1);
    }


    @Test
    public void redirectToAddQuestionPageIfNoQuestionsInOnlineTest() throws Exception {
        controller.doGet(request, response);
        assertEquals("/practice/completed_practice.jsp", response.getRedirectedUrl());
    }

    @Test
    public void redirect_to_question()
            throws Exception {
        mockQuestion();
        controller.doGet(request, response);
        assertEquals("/practice/question", response.getRedirectedUrl());
    }


    private List<Question> mockQuestion() {
        return mockQuestion(1);
    }

    private List<Question> mockQuestion(int num) {
        Category cat = Category.create("Retro");
        return IntStream.range(0, num)
                .mapToObj(index1 -> new Question("desc" + index1, "adv" + index1, cat.getId(), false, false))
                .map(Entity::save)
                .collect(Collectors.toList());
    }

    @Test
    public void userMustSeeTheQuestionIfSheHasNotDoneItBefore() throws IOException {
        mockQuestion();
        controller.doGet(request, response);
        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(1);
    }

    @Test
    public void questionCount10() throws IOException {
        mockQuestion(11);
        controller.doGet(request, response);
        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(10);
    }

    @Test
    public void questionCount_指定() throws IOException {
        mockQuestion(11);
        request.addParameter("question_count", "5");
        controller.doGet(request, response);

        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(5);
    }

    @Test
    public void questionCount_str() throws IOException {
        mockQuestion(11);
        request.addParameter("question_count", "a");
        controller.doGet(request, response);

        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(10);
    }

    @Test
    public void questionCount_0() throws IOException {
        mockQuestion(11);
        request.addParameter("question_count", "0");
        controller.doGet(request, response);

        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest).isNull();
    }

    @Test
    public void questionCount_when_no_category_given() throws IOException {
        mockQuestion(11);
        request.addParameter("categoryId", "");
        controller.doGet(request, response);

        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(10);
    }

    @Test
    public void questionCount_when_category_is_empty() throws IOException {
        mockQuestion(11);
        Category scrum = Category.create("scrum");

        request.addParameter("categoryId", scrum.stringId());
        controller.doGet(request, response);

        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest).isNull();
    }
    @Test
    public void questionCount_when_category_is_not_empty() throws IOException {
        mockQuestion(11);
        Category retro = repo(Category.class).findFirstBy("name", "Retro");

        request.addParameter("categoryId", retro.stringId());
        controller.doGet(request, response);

        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(10);
    }

}
