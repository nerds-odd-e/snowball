package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.OnlinePractice;
import com.odde.snowball.model.onlinetest.OnlineTest;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/launchPractice")
public class LaunchPracticeController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        session.setAttribute("date", LocalDate.now());

        OnlineTest onlineTest = OnlinePractice.createOnlinePractice(user);
        if (onlineTest.getCurrentQuestion() == null) {
            resp.sendRedirect("/practice/completed_practice.jsp");
            return;
        }
        session.setAttribute("onlineTest", onlineTest);
        resp.sendRedirect("/onlinetest/question");
    }
}
