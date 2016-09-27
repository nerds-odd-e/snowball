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
    MockHttpServletRequest req;
    MockHttpServletResponse res;
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
    public void rollDiceNormal() throws Exception {
        game.nextRandomNumber(6);
        game.normalPlayer1WillBeAt(2);
        assertEquals("{6,2}", getPostResponse("normal"));

        game.nextRandomNumber(5);
        game.normalPlayer1WillBeAt(1);
        assertEquals("{5,1}", getPostResponse("normal"));
    }

    @Test
    public void rollDiceSuper() throws Exception {
        game.nextRandomNumber(6);
        game.superPlayer1WillBeAt();
        assertEquals("{6,6}", getPostResponse("super"));
    }

    private String getPostResponse(String value) throws ServletException, IOException {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        req.setParameter("roll", value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }

}
