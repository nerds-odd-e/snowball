package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.ContactPerson;
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
        if (null == token) {
            resp.sendRedirect("initialize_password_token_error.jsp");
            return;
        }
        resp.sendRedirect("initialize_password.jsp?token=" + token);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String passwordConfirm = req.getParameter("password_confirm");
        if (!password.equals(passwordConfirm)) {
            resp.sendRedirect("initialize_password.jsp?error=unmatch");
            return;
        }

        if (this.validate(password)) {
            User user = User.findFirst("email = ?", req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            user.saveIt();
            resp.sendRedirect("initialize_password_success.jsp");
            return;
        }
       resp.sendRedirect("initialize_password.jsp");
    }

    boolean validate(String password) {
        return !StringUtils.isEmpty(password);
    }
}
