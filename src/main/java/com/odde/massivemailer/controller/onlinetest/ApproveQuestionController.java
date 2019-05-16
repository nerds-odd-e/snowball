package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import com.odde.massivemailer.model.onlinetest.Question;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/onlinetest/approve_question")
public class ApproveQuestionController extends AppController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Question question = Question.findById(request.getParameter("questionId"));
        question.set("is_approved", true).saveIt();
        response.sendRedirect("/onlinetest/question_list.jsp");
    }
}
