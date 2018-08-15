package com.odde.massivemailer.controller;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/initializePassword")
public class InitializePasswordController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("initialize_password.jsp");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (this.validate(req.getParameter("password"))) {
            resp.sendRedirect("initialize_password_success.jsp");
        } else {
            resp.sendRedirect("initialize_password.jsp");
        }
    }

    public boolean validate(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }
}
