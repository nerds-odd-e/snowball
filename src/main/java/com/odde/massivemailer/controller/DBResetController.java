package com.odde.massivemailer.controller;

import org.flywaydb.core.Flyway;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DBResetController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Flyway flyway = new Flyway();
        //flyway.setDataSource("jdbc:sqlite:./oddemail.db", "", "");
        //flyway.clean();
        resp.getWriter().write("haha");

    }
}
