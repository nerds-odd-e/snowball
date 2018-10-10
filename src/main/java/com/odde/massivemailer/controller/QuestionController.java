package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.AdviceResponse;
import com.odde.massivemailer.model.AnswerOption;
import com.odde.massivemailer.model.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.odde.massivemailer.util.QuestionUtil.getCorrectOptionId;
import java.util.Arrays;

@WebServlet("/question")
public class QuestionController extends AppController {

    public static final int MAX_QUESTION_COUNT = 10;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("answeredCount", 0);
        session.setAttribute("correctlyAnsweredCount", 0);
        resp.sendRedirect("question.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        int answeredCount = (int) session.getAttribute("answeredCount");
        int correctlyAnsweredCount = (int) session.getAttribute("correctlyAnsweredCount");

        String from = req.getParameter("from");

        if ("advice".equals(from)) {
            resp.sendRedirect(getRedirectPageName(answeredCount));
            return;
        }

        answeredCount++;

        session.setAttribute("answeredCount", answeredCount);

        String answeredOptionId = req.getParameter("optionId");
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        Collection<AnswerOption> optionsByQuestionId = AnswerOption.getForQuestion(questionId);


        String correctOption = getCorrectOptionId(optionsByQuestionId);


        if(correctOption.equals(answeredOptionId)){
            session.setAttribute("correctlyAnsweredCount", ++correctlyAnsweredCount);
            resp.sendRedirect(getRedirectPageName(answeredCount));
            return;
        }

        Optional<Question> questionOptional = Question.getById(questionId);
        Question question = questionOptional.isPresent()?questionOptional.get(): new Question();

        AdviceResponse adviceResponse = new AdviceResponse();
        adviceResponse.setQuestion(question.getDescription());

        List<String> optionsString = optionsByQuestionId.stream().map(o-> o.getDescription()).collect(Collectors.toList());
        adviceResponse.setOptions(optionsString);
        adviceResponse.setCorrectOption(correctOption);
        adviceResponse.setAdviceText(question.getAdvice());
        adviceResponse.setSelectedOption(answeredOptionId);
        forwardAdvicePage(req, resp, adviceResponse);
    }

    private void forwardAdvicePage(HttpServletRequest req, HttpServletResponse resp, AdviceResponse adviceResponse) throws ServletException, IOException {
        req.setAttribute("correctOption", adviceResponse.getCorrectOption());
        req.setAttribute("selectedOption", adviceResponse.getSelectedOption());

        String[] options = adviceResponse.getOptions().toArray(new String[adviceResponse.getOptions().size()]);

        req.setAttribute("options", options);
        req.setAttribute("adviceText", adviceResponse.getAdviceText());
        req.setAttribute("questionText", adviceResponse.getQuestion());
        RequestDispatcher dispatch = req.getRequestDispatcher("advice.jsp");
        dispatch.forward(req, resp);
    }

    private String getRedirectPageName(int answeredCount) {
        String redirectPageName = "end_of_test.jsp";
        if (answeredCount < MAX_QUESTION_COUNT) {
            redirectPageName = "question.jsp";
        }
        return redirectPageName;
    }


}
