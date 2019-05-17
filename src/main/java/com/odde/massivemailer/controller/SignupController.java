package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signup")
public class SignupController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User existing_user = User.findFirst("name = ?", req.getParameter("userName"));
        if (existing_user != null){
            resp.sendRedirect("/signup.jsp?status=fail");
            return;
        }

        boolean match = req.getParameter("password").equals(req.getParameter("password_confirm"));
        if(!match) {
            resp.sendRedirect("/signup.jsp?status=fail");
            return;
        }

        User user = new User(req.getParameter("email"));
        user.setUserName(req.getParameter("userName"));
        user.setPassword(req.getParameter("password"));
        user.saveIt();

        String sessionToken = RandomStringUtils.randomAlphanumeric(32);
        Cookie sessionCookie = new Cookie("session_token", sessionToken);
        sessionCookie.setHttpOnly(true);
        resp.addCookie(sessionCookie);

        String redirectUrl = "tokkun/top";
        String userName = req.getParameter("userName");
        HttpSession session = req.getSession(true);
        session.setAttribute("userName", userName);
        session.setAttribute("loggedIn", true);
        resp.sendRedirect(redirectUrl);
    }
}
