package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import com.odde.massivemailer.model.onlinetest.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/onlinetest/question")
public class QuestionController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/onlinetest/question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int correctlyAnsweredCount = (int) session.getAttribute("correctlyAnsweredCount");
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String answeredOptionId = req.getParameter("optionId");

        if(isCorrectAnswer(onlineTest, answeredOptionId)){
            correctlyAnsweredCount++;
            session.setAttribute("correctlyAnsweredCount", correctlyAnsweredCount);
            resp.sendRedirect(getRedirectPageName(onlineTest.hasNextQuestion()));
            req.setAttribute("totalScore", correctlyAnsweredCount);
            return;
        }

        req.setAttribute("selectedOption",answeredOptionId);
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/advice.jsp");
        dispatch.forward(req, resp);
    }

    private boolean isCorrectAnswer(OnlineTest onlineTest, String answeredOptionId) {
        Question currentQuestion = onlineTest.getCurrentQuestion();
        return currentQuestion.verifyAnswer(answeredOptionId);
    }

    private String getRedirectPageName(boolean moreQuestionsExist) {
        String redirectPageName = "/onlinetest/end_of_test.jsp";
        if (moreQuestionsExist) {
            redirectPageName = "/onlinetest/question.jsp";
        }
        return redirectPageName;
    }
}
