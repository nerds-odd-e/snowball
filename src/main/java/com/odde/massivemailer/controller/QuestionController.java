package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.AdviceResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int answeredCount = (int) session.getAttribute("answeredCount");

        String from = req.getParameter("from");

        if ("advice".equals(from)) {
            resp.sendRedirect(getRedirectPageName(answeredCount));
            return;
        }

        answeredCount++;
        session.setAttribute("answeredCount", answeredCount);

        String optionId = req.getParameter("optionId");
        String correctOption = "5";

        if (correctOption.equals(optionId)) {
            resp.sendRedirect(getRedirectPageName(answeredCount));
            return;
        }

        AdviceResponse adviceResponse = new AdviceResponse();
        // to do - set attributes of adviceResponse here
        // for testing
        adviceResponse.setQuestion("What is scrum?");

        adviceResponse.setOptions(Arrays.asList("Scrum is Rugby",
                "Scrum is Baseball",
                "Scrum is Soccer",
                "Scrum is Sumo",
                "None of the above"));
        adviceResponse.setCorrectOption(correctOption);
        adviceResponse.setAdviceText("Scrum is a framework for agile development.");
        adviceResponse.setSelectedOption(optionId);
        forwardAdvicePage(req, resp, adviceResponse);
    }

    private void forwardAdvicePage(HttpServletRequest req, HttpServletResponse resp, AdviceResponse adviceResponse) throws ServletException, IOException {
        req.setAttribute("correctOption", adviceResponse.getCorrectOption());
        req.setAttribute("selectedOption", adviceResponse.getSelectedOption());

        String[] options = (String[]) adviceResponse.getOptions().toArray();

        req.setAttribute("options", options);
        req.setAttribute("adviceText", adviceResponse.getAdviceText());
        req.setAttribute("questionText", adviceResponse.getQuestion());
        RequestDispatcher dispatch = req.getRequestDispatcher("advice.jsp");
        dispatch.forward(req, resp);
    }

    private String getRedirectPageName(int answeredCount) {
        String redirectPageName = "end_of_test.jsp";
        if (answeredCount < MAX_QUESTION_COUNT) {
            redirectPageName = "question.jsp";
        }
        return redirectPageName;
    }


}
