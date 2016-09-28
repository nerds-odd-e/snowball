package com.odde.massivemailer.controller;

import static org.junit.Assert.assertEquals;

import com.google.gson.JsonObject;
import com.odde.emersonsgame.stubs.StubbedGameRound;
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
    StubbedGameRound gameRound = new StubbedGameRound();

    @Before
    public void setupGame() {
        gamePlayerController.setGameRound(gameRound);
    }

    @Test
    public void returnDistance() throws Exception {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("distance", 20);

        gameRound.setDistance(20);
        assertEquals(expectedResponse.toString(), getPostResponse("distance", ""));
    }

    @Test
    public void rollDice() throws Exception {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("dieResult", 6);
        expectedResponse.addProperty("playerPos", 2);

        gameRound.nextRandomNumber(6);
        gameRound.movePlayerForNormalMode(2);
        assertEquals(expectedResponse.toString(), getPostResponse("roll", ""));

        expectedResponse = new JsonObject();
        expectedResponse.addProperty("dieResult", 5);
        expectedResponse.addProperty("playerPos", 1);

        gameRound.nextRandomNumber(5);
        gameRound.movePlayerForNormalMode(1);
        assertEquals(expectedResponse.toString(), getPostResponse("roll", ""));
    }

    private String getPostResponse(String name, String value) throws ServletException, IOException {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        req.setParameter(name, value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }

}
