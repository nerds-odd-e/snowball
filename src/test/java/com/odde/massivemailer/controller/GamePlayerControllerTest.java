package com.odde.massivemailer.controller;

import static org.junit.Assert.assertEquals;

import com.google.gson.JsonObject;
import com.odde.emersonsgame.GameRound;
import com.odde.emersonsgame.implement.GameRoundImplementation;
import com.odde.massivemailer.model.Player;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public class GamePlayerControllerTest {
    GamePlayerController gamePlayerController = new GamePlayerController();
    MockHttpServletRequest req;
    MockHttpServletResponse res;
    Player player;
    GameRound gameRound;

    @Before
    public void setupGame() {
        gameRound = new GameRoundImplementation();
        gameRound.setDistance(30);
        player = new Player();
        gamePlayerController.setGameRound(gameRound);
    }

    @Test
    public void getGameRoundObj() throws Exception {
        JsonObject expectedResponse = createJsonObj(30, 0, 0, 0);
        assertEquals(expectedResponse.toString(), getPostResponse("roll", ""));
    }

    @Test
    public void rollDice() throws Exception {
        // normal roll without any scar
        gameRound.setRandomGeneratedNumber(6);
        assertEquals(createJsonObj(30, 2, 0, 6).toString(), getPostResponse("roll", "normal"));

        // super roll without any scar
        gameRound.setRandomGeneratedNumber(6);
        assertEquals(createJsonObj(30, 8, 1, 6).toString(), getPostResponse("roll", "super"));

        // normal roll with 1 scar
        gameRound.setRandomGeneratedNumber(3);
        assertEquals(createJsonObj(30, 8, 1, 3).toString(), getPostResponse("roll", "normal"));
    }

    private JsonObject createJsonObj(int dist, int playerPos, int playerScars, int dieResult) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("distance", dist);
        jsonObject.addProperty("playerPos", playerPos);
        jsonObject.addProperty("playerScar", playerScars);
        jsonObject.addProperty("dieResult", dieResult);
        return jsonObject;
    }

    private String getPostResponse(String name, String value) throws ServletException, IOException {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        req.setParameter(name, value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }
}
