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

@WebServlet("/onlinetest/launchQuestion")
public class LaunchQuestionController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        OnlineTest onlineTest = OnlineQuiz.createOnlineQuiz(getQuestionCount(req));
        if(!onlineTest.hasNextQuestion()){
            resp.sendRedirect("add_question.jsp");
            return;
        }
        session.setAttribute("answeredCount", 0);
        session.setAttribute("onlineTest", onlineTest);
        session.setAttribute("testId", UUID.randomUUID().toString());
        session.setAttribute("alertMsg", "");
        resp.sendRedirect("/onlinetest/question.jsp");
    }

    private int getQuestionCount(HttpServletRequest req) {
        String questionCountStr = req.getParameter("question_count");
        if (questionCountStr == null)
            return 10;
        return Integer.parseInt(questionCountStr);
    }
}

