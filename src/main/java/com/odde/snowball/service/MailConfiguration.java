package com.odde.snowball.service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

public class MailConfiguration {

    private final String username;
    private final String password;
    private final ServerConfig smtp;

    public MailConfiguration(String username, String password, ServerConfig smtp) {
        this.username = username;
        this.password = password;
        this.smtp = smtp;
    }


    public Transport getSmtpTransport(Session session) throws MessagingException {
        Transport transport = session.getTransport("smtp");
        transport.connect(this.smtp.host(), this.smtp.port(),this.username, this.password);
        return transport;
    }
}
