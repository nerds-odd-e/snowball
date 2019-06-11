package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.Question;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/onlinetest/approve_question")
public class ApproveQuestionController extends AppController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Question question = repo(Question.class).findByStringId(request.getParameter("questionId"));
        question.setApproved(true);
        question.save();
        response.sendRedirect("/onlinetest/question_list.jsp");
    }
}
