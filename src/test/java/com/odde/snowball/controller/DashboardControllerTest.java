package com.odde.snowball.controller;

import com.odde.TestWithDB;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(TestWithDB.class)
public class DashboardControllerTest {
    private DashboardController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Category cat1 = Category.create("Cat1");
    private User currentUser = new User().save();

    @Before
    public void setUpMockService() {
        controller = new DashboardController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.getSession().setAttribute("currentUser", currentUser);
        Question publicQuestion = new Question("description", "advice", cat1.getId(), false, true, true, currentUser);
        publicQuestion.save();
        Question privateQuestion = new Question("description", "advice", cat1.getId(), false, true, false, currentUser);
        privateQuestion.save();
        Question questionByAnonymousUser = new Question("description", "advice", cat1.getId(), false, true);
        questionByAnonymousUser.save();
    }

    private User loginOtherUser() {
        User otherUser = new User("other@email.com");
        otherUser.setupPassword("password");
        otherUser.save();
        request.getSession().setAttribute("currentUser", otherUser);
        return otherUser;
    }

    @Test
    public void showQuestion() throws Exception {
        controller.doGet(request, response);
        Question question = ((List<Question>) request.getAttribute("questions")).get(0);
        assertNotNull(question);
        assertEquals("description", question.getDescription());
    }

    @Test
    public void showOtherUsersPublicQuestion() throws Exception {
        User otherUser = loginOtherUser();
        controller.doGet(request, response);
        Question question = ((List<Question>) request.getAttribute("questions")).get(0);
        assertNotNull(question);
        assertNotEquals(question.getCreateUser(), otherUser);
    }

    @Test
    public void showMyPrivateQuestion() throws Exception {
        controller.doGet(request, response);
        Optional<Question> questions = ((List<Question>) request.getAttribute("questions"))
                .stream()
                .filter(q -> !q.isPublic())
                .findFirst();
        assertDoesNotThrow(questions::get);
        assertFalse(questions.get().isPublic());
    }

    @Test
    public void notShowOtherUsersPrivateQuestion() throws Exception {
        loginOtherUser();
        controller.doGet(request, response);
        Optional<Question> questions = ((List<Question>) request.getAttribute("questions"))
                .stream()
                .filter(q -> !q.isPublic())
                .findFirst();
        assertFalse(questions.isPresent());
    }
}