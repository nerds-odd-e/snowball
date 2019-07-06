package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.practice.Practice;
import org.bson.types.ObjectId;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/launchPractice")
public class LaunchPracticeController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        ObjectId userId = (ObjectId) session.getAttribute("userId");

        Practice.generatePractice(userId);
        session.setAttribute("date", LocalDate.now());

        OnlineTest onlineTest = OnlineTest.createOnlinePractice(userId, req.getParameter("practice_category"));
        if (!onlineTest.hasNextQuestion()) {
            resp.sendRedirect("/practice/completed_practice.jsp");
            return;
        }
        session.setAttribute("onlineTest", onlineTest);
        resp.sendRedirect("/onlinetest/question");
    }
}
