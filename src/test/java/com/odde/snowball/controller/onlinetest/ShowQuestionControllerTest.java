package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineQuiz;
import com.odde.snowball.model.onlinetest.OnlineTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TestWithDB.class)
public class ShowQuestionControllerTest {
    private ShowQuestionController controller;
    private MockHttpServletRequest request = new MockHttpServletRequest();
    private MockHttpServletResponse response = new MockHttpServletResponse();
    private Category scrum = Category.create("Scrum");

    @Before
    public void setUpMockService() {
        controller = new ShowQuestionController();
    }

    @Test
    public void itMustRedirectToEndPageWhenNoQuestion() throws ServletException, IOException {
        showFirstQuestionOfQuizWithNQuestions(0);
        assertThat(response.getForwardedUrl()).isEqualTo("/onlinetest/end_of_test.jsp");
    }

    @Test
    public void itMustSetTheCurrentQuestion() throws ServletException, IOException {
        showFirstQuestionOfQuizWithNQuestions(1);
        assertThat(response.getForwardedUrl()).isEqualTo("/onlinetest/question.jsp");
        assertThat(request.getAttribute("currentQuestion")).isNotNull();
    }

    @Test
    public void itMustSetTheMessageFromSessionToRequest() throws ServletException, IOException {
        request.getSession(true).setAttribute("alertMsg", "msg");
        showFirstQuestionOfQuizWithNQuestions(1);
        assertThat((String) request.getAttribute("alertMsg")).isEqualTo("msg");
        assertThat((String) request.getSession().getAttribute("alertMsg")).isNull();
    }

    private void showFirstQuestionOfQuizWithNQuestions(int n) throws ServletException, IOException {
        for (int i = 0; i < n; i++) {
            QuestionBuilder.buildDefaultQuestion("Scrum").please();
        }
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(n);
        request.getSession().setAttribute("onlineTest", onlineTest);
        controller.doGet(request, response);
    }

}