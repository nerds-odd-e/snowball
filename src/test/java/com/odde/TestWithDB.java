package com.odde;

import org.javalite.activejdbc.Base;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class TestWithDB extends BlockJUnit4ClassRunner {
    public TestWithDB(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        Base.openTransaction();
        super.runChild(method, notifier);
        Base.rollbackTransaction();
    }

    @Override
    public void run(RunNotifier notifier) {
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:testdb.db", "", "");
        Base.openTransaction();
        Base.exec("delete from sqlite_sequence;");
        super.run(notifier);
        Base.rollbackTransaction();
        Base.close();
    }
}
