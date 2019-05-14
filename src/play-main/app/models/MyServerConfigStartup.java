package models;

import io.ebean.config.ServerConfig;
import io.ebean.event.ServerConfigStartup;

public class MyServerConfigStartup implements ServerConfigStartup {
    public void onStart(ServerConfig serverConfig) {
        serverConfig.setDatabaseSequenceBatchSize(1);
    }
}