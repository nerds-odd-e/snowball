package com.odde.snowball.controller;

import com.odde.snowball.model.SentMail;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.odde.snowball.model.base.Repository.repo;

@WebServlet("/listEmailDetails")
public class EmailOpenedCounterController extends AppController{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if(id == null) {
            resp.getOutputStream().print("{'error': 'null id'}");
            return;
        }
        SentMail email = repo(SentMail.class).findByStringId(id);
        if(email!=null){
            respondWithJSON(resp, email);
        }
    }




}
