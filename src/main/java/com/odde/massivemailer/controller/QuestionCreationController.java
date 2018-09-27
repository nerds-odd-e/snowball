package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/question/creation")
public class QuestionCreationController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Question> questions = Question.findAll();
        req.setAttribute("questions" , questions);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/question_creation.jsp");
        dispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map map = getParameterFromRequest(req, "body", "advice");
        Question.create("body", map.get("body"),
                "advice", map.get("advice")).saveIt();
        resp.sendRedirect("/massive_mailer/question/creation");
    }
}
