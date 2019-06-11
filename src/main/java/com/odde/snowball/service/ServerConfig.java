package com.odde.snowball.service;

public class ServerConfig {
    private final String host;
    private final int port;

    private ServerConfig(String host, int port) {
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
