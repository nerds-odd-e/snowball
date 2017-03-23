package com.odde;

import org.javalite.activejdbc.Base;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class TestWithDB extends BlockJUnit4ClassRunner {
    public TestWithDB(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:oddemail.db", "", "");
        super.run(notifier);
        Base.close();
    }
}
