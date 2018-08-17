package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/initialPassword")
public class InitializePasswordController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        if (this.fetchTokenUser(token) == null) {
            resp.sendRedirect("initialize_password_token_error.jsp");
            return;
        }
        resp.sendRedirect("initialize_password.jsp?token=" + token);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("password_confirm");
        String token = req.getParameter("token");
        if (!password.equals(passwordConfirm)) {
            resp.sendRedirect("initialize_password.jsp?error=unmatch&token=" + token);
            return;
        }

        if (!this.validate(password)) {
            resp.sendRedirect("initialize_password.jsp?error=unmatch&token=" + token);
            return;
        }

        User user = this.fetchTokenUser(token);
        if (user == null) {
            resp.sendRedirect("initialize_password_token_error.jsp");
            return;
        }
        user.setPassword(req.getParameter("password"));
        user.saveIt();
        resp.sendRedirect("initialize_password_success.jsp");
    }

    User fetchTokenUser(String token) {
        if (null == token) {
            return null;
        }
        User user = User.findFirst("token = ?", token);
        return user;
    }

    boolean validate(String password) {
        return !StringUtils.isEmpty(password);
    }
}
