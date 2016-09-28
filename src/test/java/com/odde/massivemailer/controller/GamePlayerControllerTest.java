package com.odde.massivemailer.controller;

import static org.junit.Assert.assertEquals;

import com.google.gson.JsonObject;
import com.odde.emersonsgame.stubs.StubbedGameRound;
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
    StubbedGameRound stubGameRound = new StubbedGameRound();

    @Before
    public void setupGame() {
        gamePlayerController.setGameRound(stubGameRound);
        stubGameRound.setDistance(30);
    }

    @Test
    public void getGameRoundObj() throws Exception {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("distance", 30);
        expectedResponse.addProperty("playerPos", 0);
        expectedResponse.addProperty("playerScar", 0);
        expectedResponse.addProperty("dieResult", 0);

        assertEquals(expectedResponse.toString(), getPostResponse("roll", ""));
    }

    @Test
    public void rollDice() throws Exception {
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("distance", 30);
        expectedResponse.addProperty("playerPos", 2);
        expectedResponse.addProperty("playerScar", 0);
        expectedResponse.addProperty("dieResult", 6);

        stubGameRound.nextRandomNumber(6);
        stubGameRound.getPlayerPosition(2);

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
