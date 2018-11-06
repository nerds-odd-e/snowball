package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.Quiz;

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
        Quiz quiz = new Quiz(getQuestionCount(req));
        if(!quiz.hasNextQuestion()){
            resp.sendRedirect("add_question.jsp");
            return;
        }
        session.setAttribute("answeredCount", 0);
        session.setAttribute("correctlyAnsweredCount", 0);
        session.setAttribute("quiz", quiz);
        session.setAttribute("testId", UUID.randomUUID().toString());
        resp.sendRedirect("/onlinetest/question.jsp");
    }

    private int getQuestionCount(HttpServletRequest req) {
        String questionCountStr = req.getParameter("question_count");
        if (questionCountStr == null)
            return 10;
        return Integer.parseInt(questionCountStr);
    }
}

