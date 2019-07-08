package com.odde.snowball.controller.onlinetest;

import com.odde.TestWithDB;
import com.odde.snowball.model.onlinetest.Category;
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
        datas.add(new String[]{"Scrum", "You should study scrum"});
        datas.add(new String[]{"Tech", "You should study tech"});
        datas.add(new String[]{"Team", "You should study team"});

        for (String[] data : datas) {
            request.setParameter("category", data[0]);
            controller.doGet(request, response);
            assertEquals(request.getAttribute("advice").toString(), data[1]);
        }
    }

    @Test
    public void Updateボタンを押すとUpdateAdvice画面にリダイレクトされる() throws IOException, ServletException {
        Category cat = Category.create("Scrum");
        request.setParameter("category", cat.stringId());
        request.setParameter("advice", "You should study scrum");
        controller.doPost(request, response);
        assertEquals("/onlinetest/edit_category_advice.jsp", response.getForwardedUrl());
    }
}
