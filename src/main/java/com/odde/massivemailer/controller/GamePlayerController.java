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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class GamePlayerController extends HttpServlet {

    private GameRound game;
    private Player player = new Player();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();

        if (null != req.getParameter("roll")) {
            if (req.getParameter("roll") == "normal") {
                player.playNormal(game);
            } else if (req.getParameter("roll") == "super") {
                player.playSuper(game);
            }
        }
        outputStream.print(createResponse(game.getDistance(), player).toString());
    }


    private JsonObject createResponse(int dist, Player player) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("distance", dist);
        jsonObject.addProperty("playerPos", player.getPosition());
        jsonObject.addProperty("playerScar", player.getScars());
        jsonObject.addProperty("dieResult", player.getDieResult());
        return jsonObject;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("distance", req.getParameter("distance"));
        RequestDispatcher rq = req.getRequestDispatcher("game_player.jsp");

        HttpSession session = req.getSession();
        session.setAttribute("ID", generateID(session.getAttribute("email").toString()));
        rq.forward(req, resp);
    }

    private String generateID(String email){
        return email + new Date().getTime();
    }


    public void setGameRound(GameRound game) {
        this.game = game;
    }
}
