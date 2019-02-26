package com.odde.massivemailer.model.onlinetest;

import com.odde.TestWithDB;
import org.javalite.activejdbc.LazyList;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(TestWithDB.class)
public class CategoryAdviceTest {
    @Test
    public void 全カテゴリのアドバイスがDBから取得出来る() {
        LazyList<CategoryAdvice> list = CategoryAdvice.findAll();
        assertEquals(list.toArray().length, 3);
    }

    @Test
    public void リンクがDBにある() {
        LazyList<CategoryAdvice> list = CategoryAdvice.findAll();
        list.get(0).getString("link");
    }
}