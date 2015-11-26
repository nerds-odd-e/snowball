package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Template;
import jdk.nashorn.internal.ir.debug.JSONWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Cadet on 11/26/2015.
 */
public class TemplatesController extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public void  doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Template template = new Template();
        String convertedContactToJSON = new Gson().toJson(template);
        resp.getWriter().write(convertedContactToJSON);
    }
}
