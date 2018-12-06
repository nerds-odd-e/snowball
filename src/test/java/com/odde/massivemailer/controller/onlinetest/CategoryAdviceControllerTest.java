package com.odde.massivemailer.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.onlinetest.Category;
import com.odde.massivemailer.model.onlinetest.CategoryAdvice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
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
        List<String[]> datas = new ArrayList<>();
        datas.add(new String[]{Category.SCRUM.getName(), "You should study scrum"});
        datas.add(new String[]{Category.TECH.getName(), "You should study tech"});
        datas.add(new String[]{Category.TEAM.getName(), "You should study team"});

        for (String[] data : datas) {
            request.setParameter("category", data[0]);
            controller.doGet(request, response);
            assertEquals(request.getAttribute("advice").toString(), data[1]);
        }
    }

    @Test
    public void Updateボタンを押すとUpdateAdvice画面にリダイレクトされる() throws IOException, ServletException {
        controller.doPost(request, response);
        assertEquals("/onlinetest/edit_category_advice.jsp", response.getForwardedUrl());
    }

    @Test
    public void AdviceUpdateのポストのテスト() throws IOException, SecurityException, ServletException {
        request.setParameter("category","Scrum");
        request.setParameter("advice","You should study scrum very hard");
        controller.doPost(request,response);
        assertEquals("You should study scrum very hard", CategoryAdvice.findFirst("category_id = 1").get("advice"));

    }
}
