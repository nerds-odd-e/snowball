package com.odde.massivemailer.model.onlinetest;

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
}
