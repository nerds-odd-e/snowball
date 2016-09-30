package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.emersonsgame.implement.GameRoundImplementation;
import com.odde.emersonsgame.GameRound;
import com.odde.massivemailer.model.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameSpectatorController extends HttpServlet{
    // to be removed once game instantiating is up!!!
    //GameRound game = new GameRoundImplementation();
    //Player[] players = {new Player()};

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
