package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.controller.PracticeController;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class PracticeControllerTest {
    private PracticeController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Question question;
    private OnlineTest onlineTest;
    private Category scrum = Category.create("Scrum");
    private Category tech = Category.create("Tech");
    private Category team = Category.create("Team");

    @Before
    public void setUpMockService() {
        controller = new PracticeController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void redirectToAddQuestionPageIfNoQuestionsInOnlineTest() throws Exception {
        controller.doGet(request, response);
        assertEquals("/practice/completed_practice.jsp", response.getRedirectedUrl());
    }

    @Test
    public void redirect_to_question_jsp()
            throws Exception {
        mockQuestion();
        controller.doGet(request, response);
        assertEquals("/onlinetest/question.jsp", response.getRedirectedUrl());
    }

    private List<Question> mockQuestion() {
        Category cat = Category.create("Retro");
        return IntStream.range(0, 1)
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
        User user = new User().save();
        request.getSession().setAttribute("userId", user.getId());
        List<Question> questions = mockQuestion();
        questions.get(0).answeredBy(user.getId());
        controller.doGet(request, response);
        OnlineTest onlineTest = (OnlineTest) request.getSession().getAttribute("onlineTest");
        assertThat(onlineTest.getNumberOfQuestions()).isEqualTo(0);
    }

}
