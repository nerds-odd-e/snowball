package com.odde.snowball.controller;

import com.odde.snowball.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends AppController {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = User.getUserByEmail(email);
        String password = req.getParameter("password");
        String redirectUrl;

        if (user != null && user.isPasswordCorrect(password)) {
            Cookie sessionCookie = new Cookie("session_id", email);
            sessionCookie.setHttpOnly(true);
            resp.addCookie(sessionCookie);

            HttpSession session = req.getSession();
            session.setAttribute("loggedIn", true);
            session.setAttribute("userId", user.getId());
            redirectUrl = "/dashboard";
        } else {
            redirectUrl = "/login.jsp?status=fail";
        }
        resp.sendRedirect(redirectUrl);
    }

}
