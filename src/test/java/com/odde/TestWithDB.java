package com.odde;

import com.odde.massivemailer.startup.DBMigrater;
import com.odde.massivemailer.startup.PrepareDatabaseStartupListener;
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
        Base.open("org.sqlite.JDBC", "jdbc:sqlite:testdb.db", "", "");
        dbMigrateIfNeeded();
        Base.openTransaction();
        super.run(notifier);
        Base.rollbackTransaction();
        Base.close();
    }

    private void dbMigrateIfNeeded() {
        if (!dbMigrated) {
            new DBMigrater().migrate();
            dbMigrated = true;
        }
    }
}
