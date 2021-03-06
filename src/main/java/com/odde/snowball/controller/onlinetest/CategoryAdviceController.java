package com.odde.snowball.controller.onlinetest;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/onlinetest/category/advice")
public class CategoryAdviceController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String category = Optional.ofNullable(req.getParameter("category")).orElse("");
        String advice = "";
        switch (category) {
            case "Scrum":
                advice = "You should study scrum";
                break;
            case ("Tech"):
                advice = "You should study tech";
                break;
            case "Team":
                advice = "You should study team";
                break;
        }
        req.setAttribute("advice", advice);
        // categoryに対応するadviceを取ってセットする
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/edit_category_advice.jsp");
        dispatch.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String categoryId = req.getParameter("category");
        String advice = req.getParameter("advice");
        String link = req.getParameter("link");
        Category cat = repo(Category.class).findByStringId(categoryId);
        cat.setAdvice(advice);
        cat.setLink(link);
        cat.save();
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/edit_category_advice.jsp");
        dispatch.forward(req, resp);
    }
}
