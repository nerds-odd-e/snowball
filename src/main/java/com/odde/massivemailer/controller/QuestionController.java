package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.QuestionOption;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);

        // FIXME: use Generate Test
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        if (onlineTest == null) {
            ArrayList<QuestionOption> questionOptions = new ArrayList<>();
            questionOptions.add(new QuestionOption(1L, "Yes", true));
            questionOptions.add(new QuestionOption(2L, "No", false));
            questionOptions.add(new QuestionOption(3L, "I have no idea.", false));
            List<Question> questions = new ArrayList<>();
            Question question = new Question("Is same of feature and story?", questionOptions, "","Scrum");
            question.setId(10L);
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

        int answeredCount = onlineTest.countAnsweredQuestions();
        int maxQuestionSize = onlineTest.getQuestions().size();

        if ("advice".equals(req.getParameter("from"))) {
            resp.sendRedirect(getRedirectPageName(answeredCount, maxQuestionSize));
            return;
        }

        String questionId = req.getParameter("questionId");
        String optionId = req.getParameter("optionId");

        List<Question> updatedQuestions = onlineTest.createUpdatedQuestions(questionId);
        OnlineTest updatedOnlineTest = new OnlineTest(updatedQuestions);
        session.setAttribute("onlineTest", updatedOnlineTest);

        String correctOption = "1";

        if (correctOption.equals(optionId)) {
            resp.sendRedirect(getRedirectPageName(answeredCount + 1, maxQuestionSize));
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

    private String getRedirectPageName(int answeredCount, int maxQuestionSize) {
        String redirectPageName = "end_of_test.jsp";
        if (answeredCount < maxQuestionSize) {
            redirectPageName = "question.jsp";
        }
        return redirectPageName;
    }


}
