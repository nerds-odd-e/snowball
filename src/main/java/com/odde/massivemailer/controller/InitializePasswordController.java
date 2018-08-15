package com.odde.massivemailer.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/initializePassword")
public class InitializePasswordController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("password") != null) {

        }
        resp.sendRedirect("initialize_password_success.jsp");
    }

    public boolean validate(String password) {
        if (password != null) {
            return true;
        }
        return false;
    }
}
