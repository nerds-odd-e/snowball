package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.Quiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int correctlyAnsweredCount = (int) session.getAttribute("correctlyAnsweredCount");
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        String from = req.getParameter("from");
        String answeredOptionId = req.getParameter("optionId");

        Question currentQuestion = quiz.getCurrentQuestion();

        boolean correctAnswer = currentQuestion.verifyAnswer(answeredOptionId);

        if ("advice".equals(from) || correctAnswer){
            if(correctAnswer){
                quiz.incrementAnsweredQuestions();
                correctlyAnsweredCount++;
            }
            session.setAttribute("correctlyAnsweredCount", correctlyAnsweredCount);

            resp.sendRedirect(getRedirectPageName(quiz.hasNextQuestion()));
            return;
        }

        quiz.incrementAnsweredQuestions();
        req.setAttribute("selectedOption",answeredOptionId);
        RequestDispatcher dispatch = req.getRequestDispatcher("advice.jsp");
        dispatch.forward(req, resp);
    }

    private String getRedirectPageName(boolean moreQuestionsExist) {
        String redirectPageName = "end_of_test.jsp";
        if (moreQuestionsExist) {
            redirectPageName = "question.jsp";
        }
        return redirectPageName;
    }
}
