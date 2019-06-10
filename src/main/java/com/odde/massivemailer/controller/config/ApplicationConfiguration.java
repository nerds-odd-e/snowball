package com.odde.massivemailer.controller.config;

import java.io.Serializable;

public class ApplicationConfiguration implements Serializable {
    public ApplicationConfiguration() {
    }

    public static String testDBName() {
        return "massive_mailer_test";
    }

    public String getRoot() {
        if ("test".equals(getActiveEnv()))
            return "http://localhost:8060/";
        return "http://localhost:8070/";
    }

    public String getDBName() {
        if ("test".equals(getActiveEnv()))
            return testDBName();
        return "massive_mailer";
    }

    private String getActiveEnv() {
        return System.getProperty("active_env");
    }
}