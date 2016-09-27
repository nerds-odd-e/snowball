package com.odde.massivemailer;

import static org.junit.Assert.assertEquals;
import com.odde.massivemailer.controller.GamePlayerController;
import com.odde.massivemailer.model.GameForTest;
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
        game.movePlayerForNormalMode(2);
        assertEquals("{6,2}", getPostResponse("normal"));

        game.nextRandomNumber(5);
        game.movePlayerForNormalMode(1);
        assertEquals("{5,1}", getPostResponse("normal"));
    }

    @Test
    public void rollDiceSuper() throws Exception {
        int player1Index = game.addPlayer();

        game.nextRandomNumber(6);
        game.movePlayerForSuperMode(player1Index);
        assertEquals("{6,6}", getPostResponse("super"));

        game.nextRandomNumber(6);
        Player player = game.getPlayerAtIndex(player1Index);
        player.addScar();
        game.movePlayerForSuperMode(player1Index);
        assertEquals("{6,5}", getPostResponse("super"));
    }

    private String getPostResponse(String value) throws ServletException, IOException {
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        req.setParameter("roll", value);
        gamePlayerController.doPost(req, res);
        return res.getContentAsString();
    }

}
