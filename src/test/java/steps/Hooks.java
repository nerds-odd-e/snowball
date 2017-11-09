package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import steps.driver.DBConnectionFactory;
import steps.driver.WebDriverFactory;

public class Hooks {

    @Before
    public void beforeScenario() {
        DBConnectionFactory.prepare();
    }

    @After
    public void afterScenario() {
        DBConnectionFactory.close();
        WebDriverFactory.resetAll();
    }
}
