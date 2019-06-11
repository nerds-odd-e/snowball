package com.odde.snowball.controller.category;

import com.odde.snowball.controller.AppController;
import com.odde.snowball.model.onlinetest.Category;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/category/addCategory")
public class AddQuestionCategoryController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Category.create(req.getParameter("category_name"));
        resp.sendRedirect("/onlinetest/add_question.jsp");
    }
}
