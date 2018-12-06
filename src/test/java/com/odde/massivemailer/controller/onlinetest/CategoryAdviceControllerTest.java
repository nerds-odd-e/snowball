package com.odde.massivemailer.controller.onlinetest;

import com.odde.massivemailer.model.onlinetest.Category;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CategoryAdviceControllerTest {

    private CategoryAdviceController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setUpMockService() {
        controller = new CategoryAdviceController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void Jspファイルにリダイレクトされる() throws IOException, ServletException {
        controller.doGet(request, response);
        assertEquals("/onlinetest/edit_category_advice.jsp", response.getForwardedUrl());
    }

    @Test
    public void Scrumのadviceが設定されている() throws IOException, ServletException {
        request.setParameter("category", Category.SCRUM.getName());
        controller.doGet(request, response);
        assertEquals(request.getAttribute("advice").toString(), "You should study scrum");
    }

    @Test
    public void Techのadviceが設定されている() throws IOException, ServletException {
        request.setParameter("category", Category.TECH.getName());
        controller.doGet(request, response);
        assertEquals(request.getAttribute("advice").toString(), "You should study tech");
    }

    @Test
    public void Teamのadviceが設定されている() throws IOException, ServletException {
        request.setParameter("category", Category.TEAM.getName());
        controller.doGet(request, response);
        assertEquals(request.getAttribute("advice").toString(), "You should study team");
    }

}
