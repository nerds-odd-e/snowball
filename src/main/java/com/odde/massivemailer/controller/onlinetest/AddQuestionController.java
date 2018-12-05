package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Question;
import edu.emory.mathcs.backport.java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/onlinetest/add_question")
public class AddQuestionController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!validate(req)) {
            RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/add_question.jsp");
            dispatch.forward(req, resp);
            return;
        }
        saveQuestion(req);
        resp.sendRedirect("/onlinetest/question_list.jsp");
    }

    private boolean validate(HttpServletRequest req) {
        String description = req.getParameter("description");
        if ("".equals(description)) {
            req.setAttribute("errorMessage", "Invalid inputs found!");
            return false;
        }

        String type = req.getParameter("type");
        if ("single".equals(type)) {
            String option1 = req.getParameter("option1");
            String option2 = req.getParameter("option2");
            if ("".equals(option1) || "".equals(option2)) {
                req.setAttribute("errorMessage", "Invalid inputs found!");
                return false;
            }
        } else {
            String checkbox1 = req.getParameter("checkbox1");
            String checkbox2 = req.getParameter("checkbox2");
            if ("".equals(checkbox1) || "".equals(checkbox2)) {
                req.setAttribute("errorMessage", "Invalid inputs found!");
                return false;
            }
        }

        if (req.getParameter("check") == null) {
            req.setAttribute("errorMessage", "Right answer is not selected!");
            return false;
        }
        String check = req.getParameter("check");
        String rightAnswer = req.getParameter("option" + check);
        if ("".equals(rightAnswer)) {
            req.setAttribute("errorMessage", "Invalid inputs found!");
            return false;
        }
        return true;
    }

    private void saveQuestion(HttpServletRequest req) {
        Question question = new Question(req.getParameter("description"),
                req.getParameter("advice"),
                req.getParameter("category"));
        question.saveIt();

        final String[] checks = req.getParameterValues("check");
        List<String> checksList = Arrays.asList(checks);

        String type = req.getParameter("type");
        for (int i = 0; i < 6; i++) {
            String optionDescription;
            if ("single".equals(type)) {
                optionDescription = req.getParameter("option" + (i + 1));
            } else {
                optionDescription = req.getParameter("checkbox" + (i + 1));
            }
            if (!(i > 1 && optionDescription.isEmpty())) {
                long questionId = Long.valueOf(question.get("id").toString());
                boolean isCorrect = checksList.contains(String.valueOf(i + 1));
                AnswerOption answerOption = new AnswerOption(questionId, optionDescription, isCorrect);
                answerOption.saveIt();
            }
        }
    }
}
