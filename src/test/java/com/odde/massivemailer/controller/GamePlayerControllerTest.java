package com.odde.massivemailer.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    public void testGetMoveResult() throws Exception {
        JsonObject responseObj = (JsonObject) new JsonParser().parse(makeMove(6, "normal"));
        assertNotNull(responseObj.get("distance"));
        assertNotNull(responseObj.get("playerPos"));
        assertNotNull(responseObj.get("playerScar"));
        assertEquals(responseObj.get("dieResult").getAsInt(), 6);
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


    public String makeMove(int num, String type) throws ServletException, IOException {
        gameRound.setRandomGeneratedNumber(num);
        return getPostResponse("roll", type);
    }

    private String getPostResponse(String name, String value) throws ServletException, IOException {
        req.setParameter(name, value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }
}
