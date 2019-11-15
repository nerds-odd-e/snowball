package com.odde.snowball.controller;

import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.OnlinePractice;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/dashboard")
public class DashboardController extends AppController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("currentUser") == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        User user = (User)req.getSession().getAttribute("currentUser");

        List<Question> questions = repo(Question.class).findAll()
                .stream()
                .filter(q -> q.isVisibleForUser(user))
                .collect(Collectors.toList());
        req.setAttribute("questions", questions);

        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user, 10);

        int practiceQuestionCount = onlineTest.getNumberOfQuestions();
        req.setAttribute("practiceQuestionCount", practiceQuestionCount);
        RequestDispatcher dispatch = req.getRequestDispatcher("dashboard.jsp");
        dispatch.forward(req, resp);
    }
}
