package com.odde.massivemailer.model.onlinetest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    @Test
    public void 未選択カテゴリーのテスト() {
        assertEquals(Category.UNKNOWN.getId(), 0);
        assertEquals(Category.UNKNOWN.getName(), "");
    }

    @Test
    public void スクラムカテゴリーのテスト() {
        assertEquals(Category.SCRUM.getId(), 1);
        assertEquals(Category.SCRUM.getName(), "Scrum");
    }

    @Test
    public void テックカテゴリーのテスト() {
        assertEquals(Category.TECH.getId(), 2);
        assertEquals(Category.TECH.getName(), "Tech");
    }

    @Test
    public void チームカテゴリーのテスト() {
        assertEquals(Category.TEAM.getId(), 3);
        assertEquals(Category.TEAM.getName(), "Team");
    }

    @Test
    public void カテゴリの文字列からenumを取って来るテスト() {
        assertEquals(Category.UNKNOWN, Category.findByName(""));
        assertEquals(Category.SCRUM, Category.findByName("Scrum"));
        assertEquals(Category.TECH, Category.findByName("Tech"));
        assertEquals(Category.TEAM, Category.findByName("Team"));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void カテゴリにない文字列が入力された場合はUNKNOWNが返る() {
        thrown.expect(RuntimeException.class);
        Category.findByName("foo");
    }

    @Test
    public void カテゴリidからカテゴリネームを取って来るテスト() {
        assertEquals(Category.UNKNOWN.getName(), Category.getNameById("0"));
        assertEquals(Category.SCRUM.getName(), Category.getNameById("1"));
        assertEquals(Category.TECH.getName(), Category.getNameById("2"));
        assertEquals(Category.TEAM.getName(), Category.getNameById("3"));
    }
}
