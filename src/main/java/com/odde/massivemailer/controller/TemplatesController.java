package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.service.TemplateService;
import com.odde.massivemailer.service.impl.SqliteTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Cadet on 11/26/2015.
 */
public class TemplatesController extends HttpServlet {

    private TemplateService service;

    public TemplatesController()
    {
        this.service = new SqliteTemplate();
    }

    public TemplatesController(TemplateService service)
    {
        this.service = service;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Template> templates = service.getTemplateList();
        String convertedContactToJSON = new Gson().toJson(templates);
        resp.getWriter().write(convertedContactToJSON);
    }
}
