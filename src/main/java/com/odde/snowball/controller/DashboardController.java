package com.odde.snowball.controller;

import com.odde.snowball.model.onlinetest.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/dashboard")
public class DashboardController extends AppController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("currentUser") == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }
        //TODO public もしくは 自分の作ったprivate の質問一覧を取得する
        List<Question> questions = repo(Question.class).findAll();
        req.setAttribute("questions", questions);
        RequestDispatcher dispatch = req.getRequestDispatcher("dashboard.jsp");
        dispatch.forward(req, resp);
    }
}
