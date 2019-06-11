package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Todo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/todos")
public class TodosController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        jsonResponse(resp, toJson(Todo.findAll()));
    }

}
