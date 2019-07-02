package cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class PracticeTestsStepDefs {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @And("I start a practice with 1 question")
    public void iStartAPracticeWithQuestion() {
        driver.clickButtonByName("start_practice");
    }


}
