package com.odde.massivemailer.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/question")
public class QuestionController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int answeredCount = (int) session.getAttribute("answeredCount");
        answeredCount++;
        session.setAttribute("answeredCount", answeredCount);

        String from = req.getParameter("from");
        if ("advice".equals(from)) {
            resp.sendRedirect(getRedirectPageName(answeredCount));
            return;
        }

        String optionId = req.getParameter("optionId");
        String correctOption = "5";

        if (correctOption.equals(optionId)) {
            resp.sendRedirect(getRedirectPageName(answeredCount));
            return;
        }

        forwardAdvicePage(req, resp, optionId, correctOption);
    }

    private void forwardAdvicePage(HttpServletRequest req, HttpServletResponse resp, String optionId, String correctOption) throws ServletException, IOException {
        req.setAttribute("correctOption", correctOption);
        req.setAttribute("selectedOption", optionId);

        String[] options = {
                "Scrum is Rugby",
                "Scrum is Baseball",
                "Scrum is Soccer",
                "Scrum is Sumo",
                "None of the above"
        };
        req.setAttribute("options", options);

        RequestDispatcher dispatch = req.getRequestDispatcher("advice.jsp");
        dispatch.forward(req, resp);
    }

    private String getRedirectPageName(int answeredCount) {
        String redirectPageName = "end_of_test.jsp";
        if (answeredCount < 10) {
            redirectPageName = "question.jsp";
        }
        return redirectPageName;
    }


}
