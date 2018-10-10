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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.odde.massivemailer.util.QuestionUtil.getCorrectOptionId;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);
        session.setAttribute("correctlyAnsweredCount", 0);
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int correctlyAnsweredCount = (int) session.getAttribute("correctlyAnsweredCount");
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        String from = req.getParameter("from");
        final boolean moreQuestionsExist = quiz.hasNextQuestion();
        String answeredOptionId = req.getParameter("optionId");
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        Collection<AnswerOption> optionsByQuestionId = AnswerOption.getForQuestion(questionId);
        String correctOption = getCorrectOptionId(optionsByQuestionId);
        boolean correctAnswer = correctOption.equals(answeredOptionId);

        if ("advice".equals(from) || correctAnswer){
            if(correctAnswer){
                correctlyAnsweredCount++;
            }
            session.setAttribute("correctlyAnsweredCount", correctlyAnsweredCount);
            session.setAttribute("questionId", questionId);
            session.setAttribute("optionId", answeredOptionId);

            if(moreQuestionsExist){
                Question nextQuestion = quiz.getNextQuestion();
                session.setAttribute("question", nextQuestion);
            }
            resp.sendRedirect(getRedirectPageName(moreQuestionsExist));
            return;
        }

        Optional<Question> questionOptional = Question.getById(questionId);
        Question question = questionOptional.isPresent()?questionOptional.get(): new Question();

        AdviceResponse adviceResponse = new AdviceResponse();
        adviceResponse.setQuestion(question.getDescription());

        List<String> optionsString = optionsByQuestionId.stream().map(AnswerOption::getDescription).collect(Collectors.toList());
        adviceResponse.setOptions(optionsString);
        adviceResponse.setCorrectOption(correctOption);
        adviceResponse.setAdviceText(question.getAdvice());
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


}
