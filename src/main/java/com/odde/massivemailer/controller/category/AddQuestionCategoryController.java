package com.odde.massivemailer.controller.category;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.Category;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/category/addCategory")
public class AddQuestionCategoryController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Category.createIt(req.getParameter("category_name"));
        resp.sendRedirect("/onlinetest/add_question.jsp");
    }
}
