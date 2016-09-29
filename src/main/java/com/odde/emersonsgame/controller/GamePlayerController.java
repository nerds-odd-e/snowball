package com.odde.emersonsgame.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.odde.emersonsgame.GameRound;
import com.odde.emersonsgame.exception.GameException;
import com.odde.massivemailer.model.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class GamePlayerController extends HttpServlet {

    private GameRound game;
    private Player[] players = { new Player() };

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        String jsonResponse = "{}";

        if (null != req.getParameter("players")) {
            jsonResponse = new Gson().toJson(players);
        } else if (null != req.getParameter("roll")) {
            try {
                players[0] = game.play(req.getParameter("roll"), players[0]);
                jsonResponse = createResponse(game, players[0]).toString();
            } catch (GameException e) {
                jsonResponse = e.getMessage();
            }
        }
        outputStream.print(jsonResponse);
    }

    private JsonObject createResponse(GameRound game, Player player) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("distance", game.getDistance());
        jsonObject.addProperty("playerPos", player.getPosition());
        jsonObject.addProperty("playerScar", player.getScars());
        jsonObject.addProperty("dieResult", player.getDieResult());
        return jsonObject;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rq = req.getRequestDispatcher("game_player.jsp");
        HttpSession session = req.getSession();
        session.setAttribute("ID", generateID(session.getAttribute("email").toString()));
        req.setAttribute("gameState", createResponse(game, players[0]).toString());
        rq.forward(req, resp);
    }

    private String generateID(String email){
        return email + new Date().getTime();
    }

    public void setGameRound(GameRound game) {
        this.game = game;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
