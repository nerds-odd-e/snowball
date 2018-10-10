package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;

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
        session.setAttribute("answeredCount", 0);
        session.setAttribute("QUESTSION_IDS", Question.getAllIds());
        resp.sendRedirect("question.jsp");
    }


}

