package com.odde.snowball.controller;

import com.odde.snowball.model.onlinetest.Category;
import com.odde.snowball.model.onlinetest.OnlineTest;
import com.odde.snowball.model.onlinetest.Question;
import com.odde.snowball.model.onlinetest.QuestionCollection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/launchPractice")
public class PracticeController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        List<Question> notAnswered = repo(Question.class).findBy("answered", false);
        List<Question> questions = new QuestionCollection(notAnswered).generateQuestionList(repo(Category.class).findBy("name", "Retro"), 1);
        OnlineTest onlineTest = OnlineTest.getOnlineTest(questions);
        if(!onlineTest.hasNextQuestion()){
            resp.sendRedirect("/practice/completed_practice.jsp");
            return;
        }
        session.setAttribute("answeredCount", 0);
        session.setAttribute("onlineTest", onlineTest);
        session.setAttribute("testId", UUID.randomUUID().toString());
        session.setAttribute("alertMsg", "");
        resp.sendRedirect("/onlinetest/question.jsp");
    }
    private int getQuestionCount(HttpServletRequest req) {
        String questionCountStr = req.getParameter("question_count");
        if (questionCountStr == null)
            return 10;
        return Integer.parseInt(questionCountStr);
    }
}
