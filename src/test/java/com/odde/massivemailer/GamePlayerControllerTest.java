package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import com.odde.massivemailer.controller.GamePlayerController;
import com.odde.massivemailer.model.Game;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class GamePlayerControllerTest {
    GamePlayerController gamePlayerController;
    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse res = new MockHttpServletResponse();

    @Test
    public void returnDistance() throws Exception {
        Game game = new Game();
        game.setDistance(20);

        gamePlayerController = new GamePlayerController();
        gamePlayerController.setGame(game);
        gamePlayerController.doPost(req, res);
        assertEquals("20", res.getContentAsString());
    }
}
