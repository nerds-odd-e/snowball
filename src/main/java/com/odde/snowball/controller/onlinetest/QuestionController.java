package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.Answer;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import org.bson.types.ObjectId;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/onlinetest/question")
public class QuestionController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        ObjectId userId = (ObjectId) session.getAttribute("userId");

        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        String[] answeredOptionIds = req.getParameterValues("optionId");

        if (req.getParameterValues("optionId") == null) {
            resp.sendRedirect("/onlinetest/question.jsp");
            return;
        }

        String lastDoneQuestionId = req.getParameter("lastDoneQuestionId");
        String alertMsg = onlineTest.getAlertMsg(lastDoneQuestionId);
        session.setAttribute("alertMsg", alertMsg);

        if (!lastDoneQuestionId.equals(String.valueOf(onlineTest.getNumberOfAnsweredQuestions()))) {
            resp.sendRedirect(onlineTest.getNextPageName());
            return;
        }

        Answer answer = onlineTest.answerCurrentQuestion(answeredOptionIds, userId, LocalDate.now());

        if (answer.isCorrect()) {
            resp.sendRedirect(onlineTest.getNextPageName());
            return;
        }

        req.setAttribute("selectedOption", new ArrayList(Arrays.asList(answeredOptionIds)));
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/advice.jsp");
        dispatch.forward(req, resp);
    }

}
