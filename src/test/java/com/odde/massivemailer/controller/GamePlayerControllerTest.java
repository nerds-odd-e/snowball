package com.odde.massivemailer.controller;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gson.*;
import com.odde.emersonsgame.controller.GamePlayerController;
import com.odde.emersonsgame.exception.GameException;
import com.odde.emersonsgame.implement.GameRound;
import com.odde.massivemailer.model.Player;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class GamePlayerControllerTest {
    public static final String SESSION_EMAIL = "email";
    public static final String SESSION_ID = "ID";
    public static final String PLAYER2_EMAIL = "test@test.com";
    public static final String PLAYER1_EMAIL = "some@gmail.com";

    GamePlayerController gamePlayerController = new GamePlayerController();

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    Player player;
    GameRound gameRound;


    @Before
    public void setupGame() {
        gameRound = new GameRound();
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
        loginWithEmail(PLAYER1_EMAIL);

        JsonObject expectedObj = new JsonObject();
        expectedObj.addProperty("error", GameException.INVALID_MOVE);
        assertEquals(expectedObj.toString(), getPostResponse("roll", "Expected exception"));
    }

    @Test
    public void testGetMoveResult() throws Exception {
        loginWithEmail(PLAYER1_EMAIL);

        JsonObject responseObj = (JsonObject) new JsonParser().parse(makeMove(6, "normal"));
        assertNotNull(responseObj.get("distance"));
        assertNotNull(responseObj.get("playerPos"));
        assertNotNull(responseObj.get("playerScar"));
        assertEquals(6, responseObj.get("dieResult").getAsInt());
    }

    @Test
    public void testSetDistance() throws Exception {
        GameRound game = mock(GameRound.class);
        gamePlayerController.setGameRound(game);

        req.setParameter("distance", "45");
        gamePlayerController.doPost(req, res);
        verify(game).setDistance(45);
    }

    @Test
    public void testPlayerRoll() throws Exception {
        // GIVEN: Game has 1 player
        gamePlayerController.setPlayers(makePlayersWithEmails(new String[]{PLAYER1_EMAIL}));
        // AND: Alvin logs in
        loginWithEmail("alvin");
        String playerID = req.getSession().getAttribute("ID").toString();

        // WHEN: Alvin rolls,
        req.setParameter("roll", "normal");
        req.setParameter("id", playerID);
        gamePlayerController.doPost(req, res);

        res = new MockHttpServletResponse();
        // THEN: return updated game state for alvin
        req.setRequestURI("emersonsgame/Players");
        gamePlayerController.doGet(req, res);

        String contentAsString = res.getContentAsString();
        JsonArray playersObject = (JsonArray) new JsonParser().parse(contentAsString);
        JsonObject alvinObject = (JsonObject) playersObject.get(1);
        assertEquals("alvin", alvinObject.get("email").getAsString());
        assertNotEquals(0, alvinObject.get("position").getAsInt());
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

        assertPlayerID(0, PLAYER1_EMAIL);
    }

    @Test
    public void testSecondPlayerGetsAdded() throws Exception {
        gamePlayerController.setPlayers(makePlayersWithEmails(new String[]{PLAYER1_EMAIL}));

        loginWithEmail(PLAYER2_EMAIL);

        req.setRequestURI("emersonsgame/Players");
        gamePlayerController.doGet(req, res);

        assertPlayerID(1, PLAYER2_EMAIL);
    }


    @Test
    public void testNewlyAddedPlayerCannotMove() throws Exception {
        final String testID = "testID";

        ArrayList<Player> players = new ArrayList<Player>();
        Player testPlayer = new Player();
        testPlayer.setID(testID);
        players.add(testPlayer);
        gamePlayerController.setPlayers(players);

        ArrayList<String> playerIDs = new ArrayList<String>() {{
            add(testID);
        }};
        gamePlayerController.setPlayersMoved(playerIDs);

        HttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute(SESSION_ID, testID);
        req.setSession(mockSession);
        req.setRequestURI("emersonsgame");
        req.setParameter("roll", "normal");
        gamePlayerController.doPost(req, res);

        assertTrue(res.getContentAsString().contains("error"));
    }

    @Test
    public void testPlayerCanMoveAfterNextRound() throws Exception {
        final String testID = "testID";

        ArrayList<Player> players = new ArrayList<Player>();
        Player testPlayer = new Player();
        testPlayer.setID(testID);
        players.add(testPlayer);
        gamePlayerController.setPlayers(players);

        ArrayList<String> playerIDs = new ArrayList<String>() {{
            add(testID);
        }};
        gamePlayerController.setPlayersMoved(playerIDs);

        req.setRequestURI("emersonsgame/nextround");
        gamePlayerController.doPost(req, res);

        HttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute(SESSION_ID, testID);
        req.setSession(mockSession);
        req.setRequestURI("emersonsgame");
        req.setParameter("roll", "normal");
        gamePlayerController.doPost(req, res);

        assertFalse(res.getContentAsString().contains("error"));
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

    @Ignore
    @Test
    public void testGetErrorMessageWhenPlayerHasMadeMove() throws Exception {
        gamePlayerController.addToPlayerMovedList("id");
        String expectedResponse = "{\"error\":\"Invalid turn\"}";
        assertEquals(expectedResponse, getPostResponse("roll", "normal"));
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

        for (String e : emails) {
            Player player = new Player();
            player.setEmail(e);
            players.add(player);
        }

        return players;
    }

    public void assertPlayerID(int index, String email) throws UnsupportedEncodingException {
        JsonArray playersObject = (JsonArray) new JsonParser().parse(res.getContentAsString());
        assertEquals(email, ((JsonObject) playersObject.get(index)).get("email").getAsString());
        assertNotEquals("", ((JsonObject) playersObject.get(index)).get("ID").getAsString());
    }

    public void loginWithEmail(String email) throws ServletException, IOException {
        req.setRequestURI("emersonsgame");
        req.getSession().setAttribute("email", email);
        gamePlayerController.doGet(req, res);
    }
}
