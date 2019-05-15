package com.odde.massivemailer.controller.tokkun;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import com.odde.massivemailer.model.onlinetest.Question;
import com.odde.massivemailer.model.tokkun.QuestionResponseForTokkun;
import com.odde.massivemailer.model.tokkun.Tokkun;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(TestWithDB.class)
public class TokkunControllerTest {
    private TokkunController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new TokkunController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    private Question createQuestionWithOptions(String categoryId) {
        Question question = Question.createIt("description", "What is Scrum?", "advice", "Scrum is a coding practice.", "category", categoryId);
        Long id = (Long) question.getId();
        AnswerOption.createIt("description", "desc1", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc2", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc3", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc4", "question_id", id, "is_correct", 0);
        AnswerOption.createIt("description", "desc5", "question_id", id, "is_correct", 1);

        return question;
    }

    private QuestionResponseForTokkun createQuestionForTokkun(String categoryId) {
        QuestionResponseForTokkun questionResponseForTokkun = QuestionResponseForTokkun.createIt(
                "user_id", "", "question_id", "0", "counter", 1, "answered_at", "2019-05-12 10:20:23");

        return questionResponseForTokkun;
    }

    @Test
    public void getPost() throws ServletException, IOException {
        Question question = createQuestionWithOptions("0");
//        QuestionResponseForTokkun questionResponseForTokkun = createQuestionForTokkun("0");
        Tokkun tokkun = new Tokkun(1);
        request.getSession().setAttribute("tokkun", tokkun);

//        request.getSession().getAttribute("login_user");
        String optionId = question.getFirstOptionId().toString();
        request.addParameter("optionId", optionId);
        request.addParameter("lastDoneQuestionId", "0");
        controller.doPost(request, response);

        // parameterで取得したansweredOptionIdsを次の画面に渡してることの確認。
        ArrayList<String> selectedOption =  (ArrayList<String>) request.getAttribute("selectedOption");
//        assertEquals(question.getFirstOptionId().toString(), request.getAttribute("selectedOption"));
//        assertEquals(optionId, selectedOption.get(0));
        assertEquals("a", selectedOption.get(0));
    }
}
