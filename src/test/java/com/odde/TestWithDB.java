package com.odde;

import com.odde.massivemailer.service.MongoDBConnector;
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
        MongoDBConnector.setDBName("massive_mailer_test");
        MongoDBConnector.resetAll();
        super.runChild(method, notifier);
    }
}
