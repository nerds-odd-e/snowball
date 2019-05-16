package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tokkun/tokkun_top")
public class TokkunTopController extends AppController {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = getCurrentUser(req);
        if (user == null) {
            resp.sendRedirect("/login.jsp");
        } else {
            resp.sendRedirect("/tokkun/tokkun_top.jsp");
        }
    }

}
