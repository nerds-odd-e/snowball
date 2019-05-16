package com.odde;

import com.odde.massivemailer.startup.DBMigrater;
import org.javalite.activejdbc.Base;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class TestWithDB extends BlockJUnit4ClassRunner {

    private static boolean dbMigrated = false;

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
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/massive_mailer_unittest", "root", "");
        dbMigrateIfNeeded();
        Base.openTransaction();
        super.run(notifier);
        Base.rollbackTransaction();
        Base.close();
    }

    private void dbMigrateIfNeeded() {
        if (!dbMigrated) {
            Base.exec("drop database massive_mailer_unittest;");
            Base.exec("create database massive_mailer_unittest;");
            Base.exec("use massive_mailer_unittest;");
            new DBMigrater().migrate();
            dbMigrated = true;
        }
    }
}
