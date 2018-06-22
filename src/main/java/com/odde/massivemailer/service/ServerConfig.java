package com.odde.massivemailer.service;

public class ServerConfig {
    private final String host;
    private final int port;

    public ServerConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static ServerConfig get(String host, int port) {
        return new ServerConfig(host,port);
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }
}
