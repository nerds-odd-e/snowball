package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.AnswerOption;
import com.odde.massivemailer.model.onlinetest.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/onlinetest/add_question")
public class AddQuestionController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        saveQuestion(req);
        resp.sendRedirect("/onlinetest/question_list.jsp");
    }

    private void saveQuestion(HttpServletRequest req) {
        Question question = new Question(req.getParameter("description"));
        question.saveIt();

        for (int i = 0; i < 6; i++) {
            String optionDescription = req.getParameter("option" + (i + 1));
            long questionId = Long.valueOf(question.get("id").toString());
            boolean isCorrect = i + 1 == Integer.valueOf(req.getParameter("check"));
            AnswerOption answerOption = new AnswerOption(questionId, optionDescription, isCorrect);
            answerOption.saveIt();
        }
    }
}
