package com.odde.snowball.controller;

import com.odde.snowball.model.User;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/signup")
public class SignupController extends AppController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User existing_user = repo(User.class).findFirstBy("name", req.getParameter("userName"));
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
        user.setName(req.getParameter("userName"));
        user.setupPassword(req.getParameter("password"));
        user.save();

        String sessionToken = RandomStringUtils.randomAlphanumeric(32);
        Cookie sessionCookie = new Cookie("session_token", sessionToken);
        sessionCookie.setHttpOnly(true);
        resp.addCookie(sessionCookie);

        String redirectUrl = "dashboard";
        String userName = req.getParameter("userName");
        HttpSession session = req.getSession(true);
        session.setAttribute("userName", userName);
        session.setAttribute("loggedIn", true);
        resp.sendRedirect(redirectUrl);
    }
}
