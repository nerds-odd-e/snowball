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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        request.setParameter("question_count", "1");
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
        assertEquals("/onlinetest/question", response.getRedirectedUrl());
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
    public void userMustNotSeeTheQuestionIfSheHasDoneItOnTheSameDay() throws IOException {
        Question question = mockQuestion().get(0);
        question.recordQuestionForUser(user1, LocalDate.now());
        controller.doGet(request, response);
        assertEquals("/practice/completed_practice.jsp", response.getRedirectedUrl());
    }

    @Test
    public void userMustSeeQuestionEvenIfAnsweredByAnotherUser() throws IOException {
        Question question = mockQuestion().get(0);
        question.recordQuestionForUser(user2, LocalDate.now());
        controller.doGet(request, response);
        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(1);
    }

    @Test
    public void userMustSeeOnly1QuestionEvenIfThereAre2QuestionsUnanswered() throws IOException {
        mockQuestion(2);
        controller.doGet(request, response);
        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(1);
    }
}
