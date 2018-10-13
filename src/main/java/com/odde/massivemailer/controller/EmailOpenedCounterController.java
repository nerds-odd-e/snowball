package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.SentMail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/listEmailDetails")
public class EmailOpenedCounterController extends AppController{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object id = req.getParameter("id");
        if(id == null) {
            resp.getOutputStream().print("{'error': 'null id'}");
            return;
        }
        SentMail email = SentMail.findById(id);
        if(email!=null){
            respondWithJSON(resp, email);
        }
    }




}
