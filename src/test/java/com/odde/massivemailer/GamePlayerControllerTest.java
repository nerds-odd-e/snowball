package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import com.odde.massivemailer.controller.GamePlayerController;
import com.odde.massivemailer.model.GameForTest;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public class GamePlayerControllerTest {
    GamePlayerController gamePlayerController = new GamePlayerController();
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();
    GameForTest game = new GameForTest();

    @Before
    public void setupGame() {
        gamePlayerController.setGame(game);
    }

    @Test
    public void returnDistance() throws Exception {
        game.setDistance(20);
        assertEquals("20", getPostResponse(""));
    }

    @Test
    public void rollDice() throws Exception {
        game.nextRandomNumber(6);
        game.player1WillBeAt(2);
        assertEquals("{6,2}", getPostResponse("normal"));
    }

    private String getPostResponse(String value) throws ServletException, IOException {
        req.setParameter("roll", value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }

}
