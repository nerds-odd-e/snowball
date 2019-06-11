package com.odde.snowball.controller.config;

import java.io.Serializable;

public class ApplicationConfiguration implements Serializable {
    public ApplicationConfiguration() {
    }

    public static String testDBName() {
        return "snowball_test";
    }

    public String getRoot() {
        if ("test".equals(getActiveEnv()))
            return "http://localhost:8060/";
        return "http://localhost:8070/";
    }

    public String getDBName() {
        if ("test".equals(getActiveEnv()))
            return testDBName();
        return "snowball";
    }

    private String getActiveEnv() {
        return System.getProperty("active_env");
    }
}