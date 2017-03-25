package com.odde.massivemailer.service;

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
