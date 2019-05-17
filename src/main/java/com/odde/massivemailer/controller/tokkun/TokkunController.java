package com.odde.massivemailer.controller.tokkun;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import com.odde.massivemailer.model.tokkun.QuestionResponseForTokkun;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/tokkun/question")
public class TokkunController extends AppController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        request.setAttribute("selectedOption", new ArrayList(Arrays.asList("a")));
        response.sendRedirect ("/tokkun/end_of_test.jsp");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OnlineTest onlineTest = new OnlineTest(1);
        HttpSession session = req.getSession(true);
        QuestionResponseForTokkun questionResponseForTokkun = new QuestionResponseForTokkun();

        if (questionResponseForTokkun.showAnswerQuestionIfNeeded()) {
            session.setAttribute("question", onlineTest.getCurrentQuestion());
            resp.sendRedirect ("/tokkun/tokkun_question.jsp");
        } else {
            resp.sendRedirect("/tokkun/tokkun_top.jsp");
        }
    }

}
