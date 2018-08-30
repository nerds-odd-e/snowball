package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/question")
public class QuestionController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        if ("advice".equals(from)) {
            resp.sendRedirect("end_of_test.jsp");
            return;
        }

        String optionId = req.getParameter("optionId");
        String correctOption = "5";

        if (correctOption.equals(optionId)) {
            resp.sendRedirect("end_of_test.jsp");
            return;
        }

        req.setAttribute("correctOption", correctOption);
        req.setAttribute("selectedOption", optionId);

        RequestDispatcher dispatch = req.getRequestDispatcher("advice.jsp");
        dispatch.forward(req, resp);
    }
}
