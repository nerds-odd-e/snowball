package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Todo;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/todos")
public class TodosController extends AppController {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession(true).setAttribute("json", toJson(Todo.findAll()));
        req.getRequestDispatcher("todos.jsp").forward(req, resp);
    }

    private String toJson(LazyList<Model> objects) {
        String json = String.join(",", objects.stream().map(obj -> obj.toJson(true)).collect(Collectors.toList()));
        return "[" + json + "]";
    }
}
