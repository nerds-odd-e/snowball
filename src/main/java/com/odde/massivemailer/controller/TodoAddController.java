package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Todo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/addTodo")
public class TodoAddController extends AppController {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map params = getRequestBody(req);
        Todo.createIt("title", params.get("title"), "status", "todo");
        resp.sendRedirect("todo.jsp");
    }

}
