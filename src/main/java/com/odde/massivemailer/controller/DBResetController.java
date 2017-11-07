package com.odde.massivemailer.controller;

import org.javalite.activejdbc.Base;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reset")
public class DBResetController extends AppController {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext application = getServletConfig().getServletContext();
        setTestEnv(application);
        resp.getWriter().write("db is reset");
    }

    private void setTestEnv(ServletContext application) {
        application.setAttribute("email_sender", "mock");
    }

}
