package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import com.odde.massivemailer.model.onlinetest.Question;
import org.bson.types.ObjectId;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.odde.massivemailer.model.base.Repository.repo;

@WebServlet("/onlinetest/approve_question")
public class ApproveQuestionController extends AppController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Question question = repo(Question.class).findByStringId(request.getParameter("questionId"));
        question.setApproved(true);
        question.saveIt();
        response.sendRedirect("/onlinetest/question_list.jsp");
    }
}
