package com.odde.massivemailer.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.odde.massivemailer.controller.GameSpectatorController;
import org.junit.Test;
import org.seleniumhq.jetty9.server.Request;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockRequestDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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

    @Test
    public void doGetReturnsJson() throws ServletException, IOException{
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        GameSpectatorController gsc = new GameSpectatorController();
        gsc.doGet(req, resp);
        assertEquals("\"{distance: 30}\"",resp.getContentAsString());
        assertEquals("application/json", resp.getContentType());
    }
}
