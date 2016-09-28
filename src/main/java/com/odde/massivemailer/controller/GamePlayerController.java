package com.odde.massivemailer.controller;

import com.google.gson.JsonObject;
import com.odde.emersonsgame.GameRound;
import com.odde.massivemailer.model.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GamePlayerController extends HttpServlet {

    private GameRound game;
    private Player player = new Player();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();

        if(null != req.getParameter("roll")) {
            if(req.getParameter("roll") == "normal") {
                player.playNormal(game);
            } else if(req.getParameter("roll") == "super")  {
                player.playSuper(game);
            }
        }
        outputStream.print(createResponse(game.getDistance(), player.getPosition(), player.getScars(), player.getDieResult()).toString());
    }



    private JsonObject createResponse(int dist, int playerPos, int playerScars, int dieResult) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("distance", dist);
        jsonObject.addProperty("playerPos", playerPos);
        jsonObject.addProperty("playerScar", playerScars);
        jsonObject.addProperty("dieResult", dieResult);
        return jsonObject;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rq = req.getRequestDispatcher("game_player.jsp");
        rq.forward(req,resp);
    }


    public void setGameRound(GameRound game) {
        this.game = game;
    }
}
