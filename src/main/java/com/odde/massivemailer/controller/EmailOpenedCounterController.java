package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.SentMail;
import com.odde.massivemailer.serialiser.AppGson;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/listEmailDetails")
public class EmailOpenedCounterController extends HttpServlet{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        Object id = req.getParameter("id");
        if(id == null) {
            outputStream.print("{'error': 'null id'}");
            return;
        }
        SentMail email = SentMail.findById(id);
        if(email!=null){

            String json = AppGson.getGson().toJson(email);
            outputStream.print(json);
        }
    }




}
