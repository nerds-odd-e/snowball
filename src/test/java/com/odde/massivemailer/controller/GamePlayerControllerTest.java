package com.odde.massivemailer.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.google.gson.*;
import com.odde.emersonsgame.GameRound;
import com.odde.emersonsgame.controller.GamePlayerController;
import com.odde.emersonsgame.exception.GameException;
import com.odde.emersonsgame.implement.GameRoundImplementation;
import com.odde.massivemailer.model.Player;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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
        assertEquals(30, getGetRequest().getAttribute("distance"));
    }

    @Test
    public void getGameException() throws Exception {
        assertEquals(GameException.INVALID_MOVE, getPostResponse("roll", "Expected exception"));
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
        req.setRequestURI("EmersonsGame");

        // When player login to page
        gamePlayerController.doGet(req, res);

        // Then should redirect with ID in session?
        verify(mockSession).setAttribute(eq("ID"), anyString());
    }

    @Test
    public void testPlayerGetsAdded() throws Exception {
        //GIVEN: No Players
        gamePlayerController.setPlayers(new ArrayList<Player>());

        //WHEN: Player log in
        req.setRequestURI("EmersonsGame");
        req.getSession().setAttribute("email", "some@email.com");
        gamePlayerController.doGet(req,res);

        //THEN: /Players should return 1 player info
        req.setRequestURI("EmersonsGame/Players");
        gamePlayerController.doGet(req, res);
        Player[] players = { new Player() };
        assertEquals(new Gson().toJson(players), res.getContentAsString());
    }

    private JsonObject createJsonObj(int dist, int playerPos, int playerScars, int dieResult) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("distance", dist);
        jsonObject.addProperty("playerPos", playerPos);
        jsonObject.addProperty("playerScar", playerScars);
        jsonObject.addProperty("dieResult", dieResult);
        return jsonObject;
    }

    @Test
    public void testGetPlayerStates() throws Exception {
        ArrayList<Player> players = new ArrayList<Player>() {{
            add(new Player());
        }};

        gamePlayerController.setPlayers(players);
        req.setRequestURI("/EmersonsGame/Players");
        gamePlayerController.doGet(req, res);
        assertEquals(new Gson().toJson(players), res.getContentAsString());
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

    private HttpServletRequest getGetRequest() throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("email", "test@test.com");
        req.setRequestURI("EmersonsGame");
        gamePlayerController.doGet(req, res);
        return req;
    }
}
