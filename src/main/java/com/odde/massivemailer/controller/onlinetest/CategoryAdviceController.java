package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.CategoryAdvice;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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
        if (req.getParameter("category").equals("Scrum")) {
            CategoryAdvice categoryAdvice = CategoryAdvice.findFirst("category_id = 1");
            categoryAdvice.set("advice","You should study scrum very hard");
            categoryAdvice.save();
        } else {
            CategoryAdvice categoryAdvice = CategoryAdvice.findFirst("category_id = 3");
            categoryAdvice.set("advice","You should study team very hard");
            categoryAdvice.save();
        }
        RequestDispatcher dispatch = req.getRequestDispatcher("/onlinetest/edit_category_advice.jsp");
        dispatch.forward(req, resp);
    }
}