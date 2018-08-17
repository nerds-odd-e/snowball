package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class UserTest {

    @Test
    public void testValidPassword() {
        String email = "hoge@example.com";
        String password = "hogehoge";
        User user = new User(email);
        user.setPassword(password);
        user.saveIt();

        User dbUser = User.getUserByEmail(email);
        assertNotNull(dbUser);
        assertTrue(dbUser.isPasswordCorrect(password));
    }

    @Test
    public void testInvalidPassword() {
        String email = "hoge@example.com";
        String password = "hogehoge";
        User user = new User(email);
        user.setPassword(password);
        user.saveIt();

        String invalidPassword = "invalidhoge";
        User dbUser = User.getUserByEmail(email);
        assertNotNull(dbUser);
        assertFalse(dbUser.isPasswordCorrect(invalidPassword));
    }

    @Test
    public void testFetchUserByToken() {
        String email = "hoge@example.com";
        String password = "hogehoge";
        User user = new User(email);
        user.setPassword(password);
        user.saveIt();

        User dbUser = User.findFirst("token = ?", user.getToken());
        assertNotNull(dbUser);
    }


    @Test
    public void test_create_token_longer_than_100() {
        User user = new User("aaaaa@gmail.com");

        assertTrue(user.getToken().length() >= 100);
    }
    @Test
    public void test_randam_token() {
        User userOne = new User("aaaaa@gmail.com");
        User userTwo = new User("bbbb@gmail.com");

        assertNotEquals(userOne.getToken(), userTwo.getToken());
    }
}