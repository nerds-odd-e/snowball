package com.odde.massivemailer.exception;

import javax.mail.internet.AddressException;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String s, AddressException ae) {
    }
}
