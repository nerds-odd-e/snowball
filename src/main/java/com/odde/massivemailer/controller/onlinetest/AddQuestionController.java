package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.controller.AppController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddQuestionController extends AppController {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
//        if ("".equals(description)) {
//            throw new RuntimeException("empty");
//        } else {
//
//        }
        String url = "/onlinetest/add_question.jsp?status=fail";
        resp.sendRedirect(url);

    }
}
