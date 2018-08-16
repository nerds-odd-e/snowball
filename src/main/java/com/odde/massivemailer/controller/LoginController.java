package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends AppController {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = User.getUserByEmail(email);
        String password = req.getParameter("password");
        String url;

        if (user != null && user.isPasswordCorrect(password)) {
            Cookie sessionCookie = new Cookie("session_id", email);
            sessionCookie.setSecure(true);
            sessionCookie.setHttpOnly(true);
            resp.addCookie(sessionCookie);
            url = "course_list.jsp";
        } else {
            url = "login.jsp?status=fail";
        }
        resp.sendRedirect(url);
    }

}
