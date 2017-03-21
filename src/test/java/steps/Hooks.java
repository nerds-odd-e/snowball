package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import steps.driver.WebDriverFactory;

public class Hooks {

    @Before
    public void beforeScenario() {
        WebDriverFactory.getDefaultDriver().visit("http://localhost:8070/massive_mailer/reset");
    }

    @After
    public void afterScenario() {
        WebDriverFactory.getDefaultDriver().visit("http://localhost:8070/massive_mailer/reset");
        WebDriverFactory.resetAll();
    }
}
