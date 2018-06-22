package com.odde.massivemailer.service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

public class MailConfiguration {

    private final String username;
    private final String password;
    private final ServerConfig smtp;
    private final ServerConfig imap;

    public MailConfiguration(String username, String password, ServerConfig smtp, ServerConfig imap) {
        this.username = username;
        this.password = password;
        this.smtp = smtp;
        this.imap = imap;
    }


    public Store getImapStore(Session session) throws MessagingException {
        Store store = session.getStore("imap");
        store.connect(this.imap.host(),this.imap.port(),this.username,this.password);
        return  store;
    }
    public Transport getSmtpTransport(Session session) throws MessagingException {
        Transport transport = session.getTransport("smtp");
        transport.connect(this.smtp.host(), this.smtp.port(),this.username, this.password);
        return transport;
    }
}
