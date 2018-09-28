package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.QuestionOption;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@WebServlet("/question/creation")
public class QuestionCreationController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Question> questions = Question.findAll();
        req.setAttribute("questions" , questions);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/question_creation.jsp");
        dispatcher.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map map = getParameterFromRequest(req, "body", "advice", "category", "answer_1", "answer_2", "answer_3", "answer_4", "answer_5", "answer_6");
        Question question = Question.create(
                "body", map.get("body"),
                "advice", map.get("advice"),
                "category", map.get("category"));
        question.saveIt();



        IntStream.rangeClosed(0, 5).forEach(i -> {
            if(map.get("answer_"+ (i + 1)) != null && !map.get("answer_"+ (i + 1)).toString().isEmpty())
                QuestionOption.create("question_id", question.getId(), "body", map.get("answer_" + (i + 1)), "correct", i == 0).saveIt();
        });
        resp.sendRedirect("/massive_mailer/question/creation");
    }
}
