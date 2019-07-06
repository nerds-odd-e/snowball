package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.OnlineQuiz;
import com.odde.snowball.model.onlinetest.OnlineTest;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/onlinetest/launchQuiz")
public class LaunchQuizController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(getQuestionCount(req));
        if(!onlineTest.hasNextQuestion()){
            resp.sendRedirect("add_question.jsp");
            return;
        }
        session.setAttribute("onlineTest", onlineTest);
        resp.sendRedirect("/onlinetest/question");
    }

    private int getQuestionCount(HttpServletRequest req) {
        String questionCountStr = req.getParameter("question_count");
        if (questionCountStr == null)
            return 10;
        return Integer.parseInt(questionCountStr);
    }
}

