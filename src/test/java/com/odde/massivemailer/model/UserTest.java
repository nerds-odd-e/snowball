package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class UserTest {

    @Test
    public void testIsPasswordCorrect() {
        String email = "hoge@example.com";
        String password = "hogehoge";
        User user = new User(email);
        user.setPassword(password);
        user.saveIt();

        User dbUser = User.getUserByEmail(email);
        assertNotNull(dbUser);
        assertTrue(dbUser.isPasswordCorrect(password));
    }
}