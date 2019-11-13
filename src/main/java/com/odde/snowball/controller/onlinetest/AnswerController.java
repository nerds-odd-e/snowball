package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Answer;
import com.odde.snowball.model.onlinetest.AnswerStatus;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.odde.snowball.model.base.Repository.repo;
import static java.util.Arrays.asList;

@WebServlet("/onlinetest/answer")
public class AnswerController extends AppController {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String[] selectedOtpionIds = req.getParameterValues("optionId");

        Question currentQuestion = onlineTest.getCurrentQuestion();

        if (validateQuestionOptions(req, resp, session, selectedOtpionIds, currentQuestion)) return;

        Answer answer = onlineTest.answerCurrentQuestion(asList(selectedOtpionIds), user, LocalDate.now());
        if (answer.isCorrect()) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", user.stringId());
            map.put("questionId", currentQuestion.stringId());
            repo(AnswerStatus.class).fromMap(map).save();

            redirectWithMessage(resp, session, null);
            return;
        }
        req.setAttribute("selectedOption", new ArrayList<>(asList(selectedOtpionIds)));
        req.setAttribute("currentQuestion", currentQuestion);
        req.setAttribute("progress", onlineTest.progress(-1));
        req.getRequestDispatcher("/onlinetest/advice.jsp").forward(req, resp);
    }

    private boolean validateQuestionOptions(HttpServletRequest req, HttpServletResponse resp, HttpSession session, String[] selectedOtpionIds, Question currentQuestion) throws IOException {
        if (selectedOtpionIds == null) {
            return redirectWithMessage(resp, session, "You haven't selected any option.");
        }

        if (currentQuestion == null || !currentQuestion.stringId().equals(req.getParameter("currentQuestionId"))) {
            return redirectWithMessage(resp, session, "You answered previous question twice");
        }

        return false;
    }

    private boolean redirectWithMessage(HttpServletResponse resp, HttpSession session, String msg) throws IOException {
        session.setAttribute("alertMsg", msg);
        resp.sendRedirect("/onlinetest/question");
        return true;
    }

}
