package com.odde.massivemailer.service.impl;

/**
 * Created by Cadet on 11/25/2015.
 */
public class SMTPConfiguration {

    public String FROM;
    public String PASSWD;
    public String HOST;
    public int PORT;

    public SMTPConfiguration(String username, String password, String host, int port) {
        this.FROM = username;
        this.PASSWD = password;
        this.HOST = host;
        this.PORT = port;
    }
}
