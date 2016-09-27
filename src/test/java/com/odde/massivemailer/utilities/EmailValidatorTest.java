package com.odde.massivemailer.utilities;

import org.junit.Test;
import com.odde.massivemailer.utilities.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class EmailValidatorTest {

    @Test
    public void testValidEmail() {
        String email = "email@abc.com";
        assertTrue(EmailValidator.isEmailValid(email));
    }

    @Test
    public void testInvalidEmail(){
        String email = "@abc.com";
        assertFalse(EmailValidator.isEmailValid(email));
    }
}
