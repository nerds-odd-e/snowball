package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.QuestionOption;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class AddQuestionControllerTest {
    private AddQuestionController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private Category cat1 = Category.createIt("Cat1");
    private Category cat2 = Category.createIt("Cat2");

    @Before
    public void setUpMockService() {
        controller = new AddQuestionController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    private void setupValidRequest() {
        request.setParameter("description", "aaaaaaaaaaaaaaaa");
        request.setParameter("type", "single");
        request.setParameter("category", cat1.getStringId());
        request.setParameter("option1", "option1");
        request.setParameter("option2", "option2");
        request.setParameter("option3", "option3");
        request.setParameter("option4", "option4");
        request.setParameter("option5", "option5");
        request.setParameter("option6", "option6");
        request.setParameter("is-public", "");
        request.setParameter("check", String.valueOf(1));
    }

    private void setupValidRequestForMultipleChoice() {
        request.setParameter("description", "aaaaaaaaaaaaaaaa");
        request.setParameter("type", "multiple");
        request.setParameter("category", cat2.getStringId());
        request.setParameter("checkbox1", "checkbox1");
        request.setParameter("checkbox2", "checkbox2");
        request.setParameter("checkbox3", "checkbox3");
        request.setParameter("checkbox4", "checkbox4");
        request.setParameter("checkbox5", "checkbox5");
        request.setParameter("checkbox6", "checkbox6");
        final String[] checks = new String[2];
        checks[0] = "1";
        checks[1] = "2";
        request.setParameter("check", checks);
    }

    @Test
    public void doGet() throws Exception {
        controller.doGet(request, response);
        assertThat(response.getRedirectedUrl(), containsString("/onlinetest/add_question.jsp"));
    }

    @Test
    public void doPostAddQuestion() throws Exception {
        setupValidRequest();
        controller.doPost(request, response);
        Question question = Question.repository().findAll().get(0);

        String description = request.getParameter("description");
        assertEquals(description, question.getDescription());

        assertEquals("Cat1", question.getCategoryName());

        for (int i = 0; i < 6; i++) {
            String option = request.getParameter("option" + (i + 1));
            boolean hasOption = question.getOptions().stream().anyMatch(opt -> opt.getDescription().equals(option));
            assertTrue(hasOption);
        }

        String rightOptionDescription = request.getParameter("option1");

        Optional<QuestionOption> rightAnswer = question.getOptions().stream().filter(QuestionOption::isCorrect).findFirst();
        assertEquals(rightAnswer.get().getDescription(), rightOptionDescription);
    }

    @Test
    public void doPostAddQuestion_MultipleChoice() throws Exception {
        setupValidRequestForMultipleChoice();
        controller.doPost(request, response);
        Question question = Question.repository().findAll().get(0);

        String description = request.getParameter("description");
        assertEquals(description, question.getDescription());

        for (int i = 0; i < 6; i++) {
            String option = request.getParameter("checkbox" + (i + 1));
            boolean hasOption = question.getOptions().stream().anyMatch(opt -> opt.getDescription().equals(option));
            assertTrue(hasOption);
        }

        Collection<QuestionOption> options = question.getOptions();
        assertTrue(((QuestionOption) options.toArray()[0]).isCorrect());
        assertTrue(((QuestionOption) options.toArray()[1]).isCorrect());
        assertFalse(((QuestionOption) options.toArray()[2]).isCorrect());
        assertFalse(((QuestionOption) options.toArray()[3]).isCorrect());
        assertFalse(((QuestionOption) options.toArray()[4]).isCorrect());
        assertFalse(((QuestionOption) options.toArray()[5]).isCorrect());
    }


    @Test
    public void redirectToDashboard() throws Exception {
        setupValidRequest();
        controller.doPost(request, response);
        assertEquals("/dashboard", response.getRedirectedUrl());
    }

    @Test
    public void descriptionIsEmpty() throws Exception {
        setupValidRequest();
        request.setParameter("description", "");
        controller.doPost(request, response);
        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }

    @Test
    public void option1stIsEmpty() throws Exception {
        setupValidRequest();
        request.setParameter("option1", "");
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }

    @Test
    public void option2ndIsEmpty() throws Exception {
        setupValidRequest();
        request.setParameter("option2", "");
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }

    @Test
    public void checkbox1stIsEmpty() throws Exception {
        setupValidRequestForMultipleChoice();
        request.setParameter("checkbox1", "");
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }

    @Test
    public void checkbox2ndIsEmpty() throws Exception {
        setupValidRequestForMultipleChoice();
        request.setParameter("checkbox2", "");
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }

    @Test
    public void correctAnswerIsNotSelected() throws Exception {
        setupValidRequest();
        request.setParameter("check", (String) null);
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Right answer is not selected!");
    }

    @Test
    public void doSelectedAnswerIsEmpty() throws Exception {
        setupValidRequest();
        request.setParameter("option3", "");
        request.setParameter("check", String.valueOf(3));
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }

    @Test
    public void doSelectedAnswerIsEmpty_MultipleChoice() throws Exception {
        setupValidRequestForMultipleChoice();
        request.setParameter("checkbox3", "");
        final String[] checks = new String[1];
        checks[0] = "3";
        request.setParameter("check", checks);
        controller.doPost(request, response);

        String errorMessage = String.valueOf(request.getAttribute("errorMessage"));
        assertEquals(errorMessage, "Invalid inputs found!");
    }
}
