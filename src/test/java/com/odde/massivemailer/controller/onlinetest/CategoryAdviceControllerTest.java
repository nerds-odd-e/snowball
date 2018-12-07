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
        request.setParameter("category", "Scrum");
        request.setParameter("advice", "You should study scrum");
        controller.doPost(request, response);
        assertEquals("/onlinetest/edit_category_advice.jsp", response.getForwardedUrl());
    }

    @Test
    public void AdviceUpdateのポストのテスト() throws IOException, SecurityException, ServletException {
        List<String[]> datas = new ArrayList<>();
        datas.add(new String[]{String.valueOf(Category.SCRUM.getId()), "You should study scrum very hard", "1"});
        datas.add(new String[]{String.valueOf(Category.TECH.getId()), "You should study tech very hard", "2"});
        datas.add(new String[]{String.valueOf(Category.TEAM.getId()), "You should study team very hard", "3"});

        for (String[] data : datas) {
            request.setParameter("category", data[0]);
            request.setParameter("advice", data[1]);
            String categoryIdQuery = "category_id = " + data[2];
            controller.doPost(request, response);
            assertEquals(data[1], CategoryAdvice.findFirst(categoryIdQuery).get("advice"));
        }

    }
}
