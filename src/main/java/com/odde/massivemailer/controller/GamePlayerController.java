package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Game;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GamePlayerController extends HttpServlet {

    private Game game;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream outputStream = resp.getOutputStream();
        if(req.getParameter("roll").equals("normal")) {
            outputStream.print("{" + game.rollDice(1) + "," + game.getPlayerPosition(1) + "}");
        } else if(req.getParameter("roll").equals("super")) {
            outputStream.print("{" + game.rollDice(1) + "," + game.getPlayerPosition(1) + "}");
        } else {
            outputStream.print(game.getDistance());
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
