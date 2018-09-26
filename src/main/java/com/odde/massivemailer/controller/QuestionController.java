package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Option;
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
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);

        // FIXME: use Generate Test
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        if (onlineTest == null) {
            ArrayList<Option> options = new ArrayList<>();
            options.add(new Option(1L, "Yes", false));
            options.add(new Option(2L, "No", true));
            options.add(new Option(3L, "I have no idea.", false));
            List<Question> questions = new ArrayList<>();
            Question question = new Question("Is same of feature and story?", options, "");
            questions.add(question);
            onlineTest = new OnlineTest(questions);

            session.setAttribute("onlineTest", onlineTest);
            req.setAttribute("onlineTest", onlineTest);
        }

        int allQuestionCount = onlineTest.getQuestions().size();
        int currentQuestionNumber = onlineTest.countAnsweredQuestions() + 1;
        String progressState = currentQuestionNumber + "/" + allQuestionCount;
        session.setAttribute("progressState", progressState);
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");

        String from = req.getParameter("from");
        if ("advice".equals(from)) {
            resp.sendRedirect(getRedirectPageName(session, onlineTest));
            return;
        }

        String optionId = req.getParameter("optionId");
        String correctOption = "2";
        onlineTest.getCurrentQuestion().ifPresent(q -> {
            if (correctOption.equals(optionId)) {
                q.setAnsweredOptionId(Long.parseLong(optionId));
                List<Question> filteredQuestions = onlineTest.getQuestions().stream().filter(q2 -> q2.getDescription().equals(q.getDescription())).collect(Collectors.toList());
                filteredQuestions.add(q);
                session.setAttribute("onlineTest", new OnlineTest(filteredQuestions));
            }
        });

        if (correctOption.equals(optionId)) {
            resp.sendRedirect(getRedirectPageName(session, onlineTest));
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

    private String getRedirectPageName(HttpSession session, OnlineTest onlineTest) {
        String redirectPageName = "question.jsp";
        if (onlineTest.isOver()) {
            session.removeAttribute("onlineTest");
            redirectPageName = "end_of_test.jsp";
        }
        return redirectPageName;
    }


}
