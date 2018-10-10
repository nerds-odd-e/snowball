package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@WebServlet("/launchQuestion")
public class LaunchQuestionController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);
        //Get list of question IDs || x number of questions
        Stream<Long> questionIds = Question.getAllIds();
        session.setAttribute("questionIds", questionIds);
        //get question and options from database based on ID
        //Question.getById(questionIds.findFirst().get());
        //session.setAttribute("question", question);
        resp.sendRedirect("question.jsp");
    }


}

