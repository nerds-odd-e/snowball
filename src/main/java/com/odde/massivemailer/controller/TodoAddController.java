package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.Todo;

import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/addTodo")
public class TodoAddController extends TodosController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String result = req.getReader().lines().collect(Collectors.joining("\r\n"));
        Map map = new Gson().fromJson(result, Map.class);
        Todo.createIt("title", map.get("title"), "status", "todo");
        resp.sendRedirect("todo.jsp");
    }

}
