package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Todo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/todo")
public class TodoController extends AppController {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Map requestBody = getRequestBody(req);
        Todo.createIt("title", requestBody.get("inputValue"),"status","0");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        jsonResponse(res, toJson(Todo.findAll()));
    }
}
