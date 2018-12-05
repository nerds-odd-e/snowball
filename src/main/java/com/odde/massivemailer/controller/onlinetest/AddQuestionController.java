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
import java.util.List;

@WebServlet("/onlinetest/add_question")
public class AddQuestionController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMsg = checkParameter(req);
        if (!errorMsg.isEmpty()) {
            req.setAttribute("errorMessage", errorMsg);
            RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/add_question.jsp");
            dispatch.forward(req, resp);
            return;
        }
        saveQuestion(req);
        resp.sendRedirect("/onlinetest/question_list.jsp");
    }

    private String checkParameter(HttpServletRequest req) {
        if ("".equals(req.getParameter("description"))) {
            return "Invalid inputs found!";
        }
        String paramName = getOptionParamName(req);
        if ("".equals(req.getParameter(paramName + "1"))) {
            return "Invalid inputs found!";
        }
        if ("".equals(req.getParameter(paramName + "2"))) {
            return "Invalid inputs found!";
        }
        String check = req.getParameter("check");
        if (check == null) {
            return "Right answer is not selected!";
        }
        if ("".equals(req.getParameter("option" + check))) {
            return "Invalid inputs found!";
        }
        return "";
    }

    private String getOptionParamName(HttpServletRequest req) {
        String type = req.getParameter("type");
        String paramName;
        if ("single".equals(type)) {
            paramName = "option";
        } else {
            paramName = "checkbox";
        }
        return paramName;
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
