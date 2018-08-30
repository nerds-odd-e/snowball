package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/question")
public class QuestionController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String optionId = req.getParameter("optionIds");
        String correctOption = "5";

        if (correctOption.equals(optionId)) {
            resp.sendRedirect("end_of_test.jsp");
            return;
        }

        resp.sendRedirect("advice.jsp");
    }
}
