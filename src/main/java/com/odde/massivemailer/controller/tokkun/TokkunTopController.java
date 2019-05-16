package com.odde.massivemailer.controller.tokkun;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.Question;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tokkun/top")
public class TokkunTopController extends AppController {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Question> questions = Question.getAll();
        req.setAttribute("questions", questions);
        RequestDispatcher dispatch = req.getRequestDispatcher("/tokkun/tokkun_top.jsp");
        dispatch.forward(req, resp);
    }
}
