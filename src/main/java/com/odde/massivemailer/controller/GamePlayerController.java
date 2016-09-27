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
        if(req.getParameter("roll").equals("normal"))
        {
            int dice = game.player1Roll();
            int player1Pos = game.getPlayer1Position();

            outputStream.print("{" + dice + "," + player1Pos + "}");

        }
        else
        {
            outputStream.print(game.getDistance());
        }

    }

    public void setGame(Game game) {
        this.game = game;
    }
}
