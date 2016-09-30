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
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GamePlayerControllerTest {
    public static final String SESSION_EMAIL = "email";
    public static final String PLAYER2_EMAIL = "test@test.com";
    public static final String PLAYER1_EMAIL = "some@gmail.com";
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
        JsonObject expectedObj = new JsonObject();
        expectedObj.addProperty("error", GameException.INVALID_MOVE);
        assertEquals(expectedObj.toString(), getPostResponse("roll", "Expected exception"));
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
        when(mockSession.getAttribute(SESSION_EMAIL)).thenReturn(PLAYER1_EMAIL);
        req.setSession(mockSession);
        req.setRequestURI("emersonsgame");

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
        loginWithEmail(PLAYER1_EMAIL);

        //THEN: /Players should return 1 player info
        req.setRequestURI("emersonsgame/Players");
        gamePlayerController.doGet(req, res);

        ArrayList<Player> players = makePlayersWithEmails(new String[]{PLAYER1_EMAIL});
        assertEquals(new Gson().toJson(players), res.getContentAsString());
    }

    @Test
    public void testSecondPlayerGetsAdded() throws Exception {
        gamePlayerController.setPlayers(makePlayersWithEmails(new String[] {PLAYER1_EMAIL}));

        loginWithEmail(PLAYER2_EMAIL);

        req.setRequestURI("emersonsgame/Players");
        gamePlayerController.doGet(req, res);
        ArrayList<Player> players = makePlayersWithEmails(new String[] {PLAYER1_EMAIL, PLAYER2_EMAIL});
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
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player());

        gamePlayerController.setPlayers(players);
        req.setRequestURI("/emersonsgame/Players");
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
        loginWithEmail(PLAYER2_EMAIL);
        return req;
    }

    public ArrayList<Player> makePlayersWithEmails(String[] emails) {
        ArrayList<Player> players = new ArrayList<>();

        for (String e: emails) {
            Player player = new Player();
            player.setEmail(e);
            players.add(player);
        }

        return players;
    }

    public void loginWithEmail(String email) throws ServletException, IOException {
        req.setRequestURI("emersonsgame");
        req.getSession().setAttribute("email", email);
        gamePlayerController.doGet(req, res);
    }
}
