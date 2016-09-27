package com.odde.massivemailer.utilities;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailValidator {

    public static boolean isEmailValid(String email){
        try {
            new InternetAddress(email).validate();
        }catch(AddressException e){
            return false;
        }

        return true;
    }

}
