package com.odde.emersonsgame.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.odde.emersonsgame.exception.GameException;
import com.odde.emersonsgame.implement.GameRound;
import com.odde.massivemailer.model.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class GamePlayerController extends HttpServlet {

    private GameRound game = new GameRound();
    private ArrayList<Player> players = new ArrayList<Player>() {{
        add(new Player());
    }};
    private ArrayList<String> playersMovedList = new ArrayList<String>();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        String jsonResponse = "{}";

        if (req.getRequestURI().endsWith("emersonsgame/nextround")) {
            startNextRound();
        } else {
            if (null != req.getParameter("roll")) {
                // get current Player alan
                String playerID = req.getSession().getAttribute("ID").toString();
                for (int i = 0; i < players.size(); ++i) {
                    if (players.get(i).getID().equals(playerID)) {
                        if (hasPlayerMoved(players.get(i))) {
                            jsonResponse = createErrorResponse("Invalid turn");
                            break;
                        }
                        if ("normal".equals(req.getParameter("roll"))) {
                            players.set(i, game.playNormal(players.get(i)));
                            jsonResponse = createResponse(game, players.get(i)).toString();
                        } else if ("super".equals(req.getParameter("roll"))) {
                            players.set(i, game.playSuper(players.get(i)));
                            jsonResponse = createResponse(game, players.get(i)).toString();
                        } else {
                            jsonResponse = createErrorResponse("Invalid move");
                        }
                        break;
                    }
                }
                outputStream.print(jsonResponse);
            }
        }
    }

    private void startNextRound() {
        playersMovedList.clear();
    }

    public void addToPlayerMovedList(String playerId) {
        playersMovedList.add(playerId);
    }

    private boolean hasPlayerMoved(Player player) {
        return playersMovedList.contains(player.getID());
    }

    private String createErrorResponse(String errMsg) {
        return "{\"error\":\"" + errMsg + "\"}";
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
        if (req.getRequestURI().endsWith("emersonsgame")) {
            handlePlayerRequest(req, resp);
        } else if (req.getRequestURI().endsWith("emersonsgame/Players")) {
            handleListPlayers(resp);
        }
    }

    public void handlePlayerRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("distance", game.getDistance());
        if (null == session.getAttribute("ID")) {
            // New player

            String playerID = generateID(session.getAttribute("email").toString());
            session.setAttribute("ID", playerID);
            // Add to player array
            Player p = new Player();
            p.setID(playerID);
            p.setEmail(session.getAttribute("email").toString());
            players.add(p);
//            playersMovedList.add(playerID);
        }

        RequestDispatcher rq = req.getRequestDispatcher("game_player.jsp");
        rq.forward(req, resp);
    }

    public void handleListPlayers(HttpServletResponse resp) throws IOException {
        String jsonResponse = new Gson().toJson(players.toArray());
        resp.getOutputStream().print(jsonResponse);
    }

    private String generateID(String email) {
        return email + new Date().getTime();
    }

    public void setGameRound(GameRound game) {
        this.game = game;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
