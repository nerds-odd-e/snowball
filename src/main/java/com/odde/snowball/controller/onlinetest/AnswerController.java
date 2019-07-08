package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Answer;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.util.Arrays.asList;

@WebServlet("/onlinetest/answer")
public class AnswerController extends AppController {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String[] selectedOtpionIds = req.getParameterValues("optionId");

        if (selectedOtpionIds == null) {
            redirectToShowQuestionWithMsg(resp, session, "You haven't selected any option.");
            return;
        }

        Question currentQuestion = onlineTest.getCurrentQuestion();
        if (currentQuestion == null || !currentQuestion.getStringId().equals(req.getParameter("currentQuestionId"))) {
            redirectToShowQuestionWithMsg(resp, session, "You answered previous question twice");
            return;
        }

        Answer answer = onlineTest.answerCurrentQuestion(asList(selectedOtpionIds), user, LocalDate.now());
        if (answer.isCorrect()) {
            redirectToShowQuestionWithMsg(resp, session, null);
            return;
        }
        req.setAttribute("selectedOption", new ArrayList(asList(selectedOtpionIds)));
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/advice.jsp");
        dispatch.forward(req, resp);
    }

    private void redirectToShowQuestionWithMsg(HttpServletResponse resp, HttpSession session, String msg) throws IOException {
        session.setAttribute("alertMsg", msg);
        resp.sendRedirect("/onlinetest/question");
    }

}
