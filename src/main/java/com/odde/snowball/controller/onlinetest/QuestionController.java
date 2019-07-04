package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.enumeration.TestType;
import com.odde.snowball.model.onlinetest.Answer;
import com.odde.snowball.model.onlinetest.OnlineTest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/onlinetest/question")
public class QuestionController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String[] answeredOptionIds = req.getParameterValues("optionId");

        if (req.getParameterValues("optionId") == null) {
            resp.sendRedirect(getRedirectPageName(true,onlineTest.getTestType()));
            return;
        }

        String lastDoneQuestionId = req.getParameter("lastDoneQuestionId");
        String alertMsg = onlineTest.getAlertMsg(lastDoneQuestionId);
        session.setAttribute("alertMsg", alertMsg);

        if (!lastDoneQuestionId.equals(String.valueOf(onlineTest.getNumberOfAnsweredQuestions()))) {
            resp.sendRedirect(getRedirectPageName(onlineTest.hasNextQuestion(),onlineTest.getTestType()));
            return;
        }

        Answer answer = onlineTest.answerCurrentQuestion(answeredOptionIds);
        if (answer.isCorrect()) {
            resp.sendRedirect(getRedirectPageName(onlineTest.hasNextQuestion(),onlineTest.getTestType()));
            return;
        }
        req.setAttribute("selectedOption", new ArrayList(Arrays.asList(answeredOptionIds)));
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/advice.jsp");
        dispatch.forward(req, resp);
    }

    public String getRedirectPageName(boolean moreQuestionsExist, TestType practice) {
        if (moreQuestionsExist) {
            return "/onlinetest/question.jsp";
        }
        String redirectPageName = "/onlinetest/end_of_test.jsp";
        if( practice == TestType.Practice)
            redirectPageName = "/practice/completed_practice.jsp";
        return redirectPageName;
    }
}
