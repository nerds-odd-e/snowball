package com.odde.massivemailer.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

public class GameSpectatorControllerTest {
    @Test
    public void doPostRedirectsToSpecView() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        req.setParameter("distance", "15");

        GameSpectatorController gsc = new GameSpectatorController();
        gsc.doPost(req, resp);



        assertEquals("game_spectator.jsp", resp.getForwardedUrl());
        assertEquals("15", req.getParameter("distance"));
    }

//    @Test
//    public void doGetReturnsJson() throws ServletException, IOException{
//        MockHttpServletRequest req = new MockHttpServletRequest();
//        MockHttpServletResponse resp = new MockHttpServletResponse();
//
//        GameSpectatorController gsc = new GameSpectatorController();
//        gsc.doGet(req, resp);
//        assertEquals("\"{distance: 30}\"",resp.getContentAsString());
//        assertEquals("application/json", resp.getContentType());
//    }
}
