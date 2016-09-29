package com.odde.massivemailer.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import com.odde.emersonsgame.GameRound;
import com.odde.emersonsgame.implement.GameRoundImplementation;
import com.odde.massivemailer.model.Player;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GamePlayerControllerTest {
    public static final String SESSION_EMAIL = "email";
    GamePlayerController gamePlayerController = new GamePlayerController();
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
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
    public void testGetGameRoundObj() throws Exception {
        JsonObject expectedResponse = createJsonObj(30, 0, 0, 0);
        assertEquals(expectedResponse.toString(), getPostResponse("roll", ""));
    }

    @Test
    public void testValidMove() throws Exception {
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

    @Test
    public void testGetPlayerWithID() throws Exception {
        // Given email addr
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute(SESSION_EMAIL)).thenReturn("abc@gmail.com");
        req.setSession(mockSession);

        // When player login to page
        gamePlayerController.doGet(req, res);

        // Then should redirect with ID in session?
        verify(mockSession).setAttribute(eq("ID"), anyString());
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
        req.setParameter(name, value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }
}
