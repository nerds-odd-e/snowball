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

        if(null != req.getParameter("roll")) {
            if(req.getParameter("roll") == "normal") {
                game.rollDieForPlayer(1, req.getParameter("roll"));
            } else {
                game.rollDieForPlayer(1, req.getParameter("roll"));
            }
        }
        outputStream.print(game.getGameRound());
    }

    public void setGameRound(GameRound game) {
        this.game = game;
    }
}
