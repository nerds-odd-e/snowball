package com.odde.massivemailer.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameSpectatorController extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rq = req.getRequestDispatcher("game_spectator.jsp");
        rq.forward(req,resp);
    }

//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // to be removed once game instantiating is up!!!
//        game.setDistance(30);
//
//        String convertedContactToJSON = new Gson().toJson(game);
//        resp.setContentType("application/json");
//        ServletOutputStream outputStream = resp.getOutputStream();
//        outputStream.print(convertedContactToJSON);
//    }
}
