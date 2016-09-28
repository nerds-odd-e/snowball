package com.odde.massivemailer.controller;

import com.google.gson.JsonObject;
import com.odde.emersonsgame.GameRound;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GamePlayerController extends HttpServlet {

    private GameRound game;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        JsonObject response = new JsonObject();

        if(null != req.getParameter("distance")) {
            response.addProperty("distance", game.getDistance());
        } else if(null != req.getParameter("roll")) {
            response.addProperty("dieResult", game.rollDice(1));
            response.addProperty("playerPos", game.getPlayerPosition(1));
        }
        outputStream.print(response.toString());
    }

    public void setGameRound(GameRound game) {
        this.game = game;
    }
}
