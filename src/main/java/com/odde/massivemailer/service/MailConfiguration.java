package com.odde.massivemailer.service;

public class MailConfiguration {

    public String FROM;
    public String PASSWD;
    public String HOST;
    public int SMTP_PORT;
    public int IMAP_PORT;


    public MailConfiguration(String username, String password, String host, int smtpPort, int imapPort) {
        this.FROM = username;
        this.PASSWD = password;
        this.HOST = host;
        this.SMTP_PORT = smtpPort;
        this.IMAP_PORT = imapPort;
    }
}
