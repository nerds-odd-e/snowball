package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.UserAnswer;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static java.util.Arrays.asList;

@WebServlet("/onlinetest/answer")
public class UserAnswerController extends AppController {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String[] selectedOptionIds = req.getParameterValues("optionId");

        Question currentQuestion = onlineTest.getCurrentQuestion();

        if (isValidRequest(req, resp, session, selectedOptionIds, currentQuestion)) return;

        String dateString = (String)req.getSession().getAttribute("onlineTestStartDate");
        LocalDate localDate;

        if(StringUtils.isEmpty(dateString)) {
            localDate = LocalDate.now();
        } else {
            localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        }

        UserAnswer answer = onlineTest.answerCurrentQuestion(asList(selectedOptionIds), user, localDate);
        answer.setUser(user);
        answer.setUserId(user.stringId());

        boolean isPractice = false;
        if (session.getAttribute("isPractice") != null) {
            isPractice = true;
        }

        if (saveAnswerStatus(answer, isPractice)) {
            redirectWithMessage(resp, session, null);
            return;
        }
        req.setAttribute("selectedOption", new ArrayList<>(asList(selectedOptionIds)));
        req.setAttribute("currentQuestion", currentQuestion);
        req.setAttribute("progress", onlineTest.progress(-1));
        req.getRequestDispatcher("/onlinetest/advice.jsp").forward(req, resp);
    }

    private boolean saveAnswerStatus(UserAnswer answer, boolean isPractice) {
        if (isPractice && answer.isCorrect()) {
            answer.save();
            return true;
        }
        return false;
    }

    private boolean isValidRequest(HttpServletRequest req, HttpServletResponse resp, HttpSession session, String[] selectedOtpionIds, Question currentQuestion) throws IOException {
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
