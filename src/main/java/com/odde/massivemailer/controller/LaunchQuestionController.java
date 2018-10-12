package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/launchQuestion")
public class LaunchQuestionController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Quiz quiz = new Quiz();
        if(!quiz.hasNextQuestion()){
            resp.sendRedirect("add_question.jsp");
            return;
        }
        session.setAttribute("answeredCount", 0);
        session.setAttribute("correctlyAnsweredCount", 0);
        session.setAttribute("quiz", quiz);
        resp.sendRedirect("question.jsp");
    }
}

