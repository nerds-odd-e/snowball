package com.odde.massivemailer.service.exception;

import java.io.IOException;

public class GeoServiceException extends Exception {
    private Exception exception;

    public GeoServiceException(Exception e) {
        exception = e;
    }
}
