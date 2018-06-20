package com.odde.massivemailer.exception;

public class MailBoxReadException  extends RuntimeException{

    public MailBoxReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
