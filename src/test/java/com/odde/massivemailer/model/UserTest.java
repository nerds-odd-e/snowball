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
        User user = new User(email, "123");
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
        User user = new User(email, "123");
        user.setPassword(password);
        user.saveIt();

        String invalidPassword = "invalidhoge";
        User dbUser = User.getUserByEmail(email);
        assertNotNull(dbUser);
        assertFalse(dbUser.isPasswordCorrect(invalidPassword));
    }
}