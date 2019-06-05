package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Todo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/todos")
public class TodosController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json= String.join(",", Todo.findAll().stream().map(todo -> todo.toJson(true)).collect(Collectors.toList()));
        req.setAttribute("json", "["+json+"]");
        resp.sendRedirect("todos.jsp");
    }
}
