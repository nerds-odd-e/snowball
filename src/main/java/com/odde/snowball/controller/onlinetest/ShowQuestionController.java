package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/onlinetest/question")
public class ShowQuestionController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        exchangeSessionAndRequestAttributes(req, session);
        OnlineTest onlineTest = (OnlineTest) session.getAttribute("onlineTest");
        Question currentQuestion = onlineTest.getCurrentQuestion();
        if(currentQuestion != null) {
            req.setAttribute("currentQuestion", currentQuestion);
            req.setAttribute("progress", onlineTest.progress(0));
            req.getRequestDispatcher("/onlinetest/question.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("testResult", onlineTest.testResult());
        req.getRequestDispatcher(onlineTest.endPageName()).forward(req, resp);
    }

    private void exchangeSessionAndRequestAttributes(HttpServletRequest req, HttpSession session) {
        req.setAttribute("alertMsg", (String) session.getAttribute("alertMsg"));
        session.setAttribute("alertMsg", null);
    }
}
