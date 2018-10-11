package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.AdviceResponse;
import com.odde.massivemailer.model.AnswerOption;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.Quiz;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        getNextQuestion(req);
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int correctlyAnsweredCount = (int) session.getAttribute("correctlyAnsweredCount");
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        String from = req.getParameter("from");

        final boolean moreQuestionsExist = quiz.hasNextQuestion();

        if ("advice".equals(from)) {
            resp.sendRedirect(getRedirectPageName(moreQuestionsExist));
            return;
        }

        String answeredOptionId = req.getParameter("optionId");

        Question currentQuestion = quiz.getCurrentQuestion().get();
        boolean correctAnswer = currentQuestion.verifyAnswer(answeredOptionId);

        if (correctAnswer){

            correctlyAnsweredCount++;

            session.setAttribute("correctlyAnsweredCount", correctlyAnsweredCount);
            session.setAttribute("optionId", answeredOptionId);

            if(moreQuestionsExist){
                Question nextQuestion = quiz.getCurrentQuestion().get();
                session.setAttribute("question", nextQuestion);
            }
            resp.sendRedirect(getRedirectPageName(moreQuestionsExist));
            return;
        }

        AdviceResponse adviceResponse = new AdviceResponse();
        adviceResponse.setQuestion(currentQuestion.getDescription());

        String correctOption = currentQuestion.getOptions().stream().filter(AnswerOption::isCorrect).findFirst().get().getLongId().toString(); //TODO: remove when advice page refactored to use quiz
        List<String> optionsString = currentQuestion.getOptions().stream().map(AnswerOption::getDescription).collect(Collectors.toList());
        adviceResponse.setOptions(optionsString);
        adviceResponse.setCorrectOption(correctOption);
        adviceResponse.setAdviceText(currentQuestion.getAdvice());
        adviceResponse.setSelectedOption(answeredOptionId);
        forwardAdvicePage(req, resp, adviceResponse);
    }

    private void forwardAdvicePage(HttpServletRequest req, HttpServletResponse resp, AdviceResponse adviceResponse) throws ServletException, IOException {
        req.setAttribute("correctOption", adviceResponse.getCorrectOption());
        req.setAttribute("selectedOption", adviceResponse.getSelectedOption());

        String[] options = adviceResponse.getOptions().toArray(new String[0]);

        req.setAttribute("options", options);
        req.setAttribute("adviceText", adviceResponse.getAdviceText());
        req.setAttribute("questionText", adviceResponse.getQuestion());
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

    private void getNextQuestion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Quiz inProgressQuiz = (Quiz) session.getAttribute("quiz");

        if (inProgressQuiz.hasNextQuestion()) {
            Question nextQuestion = inProgressQuiz.getCurrentQuestion().get();
            session.setAttribute("question", nextQuestion);
        } else {
            session.removeAttribute("question");
        }
    }
}
