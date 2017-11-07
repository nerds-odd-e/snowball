package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import steps.driver.WebDriverFactory;

public class Hooks {

    @Before
    public void beforeScenario() {
        DBConnectionFactory.clean();
    }

    @After
    public void afterScenario() {
        WebDriverFactory.resetAll();
    }
}
