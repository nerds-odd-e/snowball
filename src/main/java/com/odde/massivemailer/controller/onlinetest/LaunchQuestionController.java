package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;

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
        OnlineTest onlineTest = new OnlineTest(getQuestionCount(req));
        if(!onlineTest.hasNextQuestion()){
            resp.sendRedirect("add_question.jsp");
            return;
        }
        session.setAttribute("answeredCount", 0);
        session.setAttribute("onlineTest", onlineTest);
        session.setAttribute("testId", UUID.randomUUID().toString());
        session.setAttribute("alertMsg", "");
        resp.sendRedirect("/onlinetest/question");
    }

    private int getQuestionCount(HttpServletRequest req) {
        String questionCountStr = req.getParameter("question_count");
        if (questionCountStr == null)
            return 10;
        return Integer.parseInt(questionCountStr);
    }
}

