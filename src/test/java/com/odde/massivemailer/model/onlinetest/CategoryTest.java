package com.odde.massivemailer.model.onlinetest;

import gherkin.lexer.Ca;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    @Test
    public void スクラムカテゴリーのテスト() {
        // id = 1
        assertEquals(Category.SCRUM.getId(), 1);
        // name = "Scrum"
        assertEquals(Category.SCRUM.getName(), "Scrum");
    }

    @Test
    public void テックカテゴリーのテスト() {
        // id = 2
        assertEquals(Category.TECH.getId(), 2);
        // name = "Tech"
        assertEquals(Category.TECH.getName(), "Tech");
    }

    @Test
    public void チームカテゴリーのテスト() {
        // id = 3
        assertEquals(Category.TEAM.getId(), 3);
        // name = "Team"
        assertEquals(Category.TEAM.getName(), "Team");
    }

    @Test
    public void カテゴリの文字列からenumを取って来るテスト() {
        assertEquals(Category.SCRUM, Category.findByName("Scrum"));
        assertEquals(Category.TECH, Category.findByName("Tech"));
        assertEquals(Category.TEAM, Category.findByName("Team"));
    }

    @Test(expected = RuntimeException.class)
    public void カテゴリにない文字列が入力された場合は例外が投げられる() {
        Category.findByName("foo");
    }
}
