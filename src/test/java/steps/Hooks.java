package steps;

import com.odde.massivemailer.service.MongoDBConnector;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class Hooks {

    @Before
    public void beforeScenario() {
        MongoDBConnector.resetAll();
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriverWrapper defaultDriver = WebDriverFactory.getDefaultDriver();
            defaultDriver.takeScreenshot("tmp/"+scenario.getName());
        }
        WebDriverFactory.resetAll();
    }

}
