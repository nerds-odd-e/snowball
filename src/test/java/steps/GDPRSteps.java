package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class GDPRSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^Visit Front page$")
    @When("^I open the main page$")
    public void iOpenTheMainPage() {
       driver.visit(site.baseUrl());
    }

    @Then("^System shows GDPR menu on left panel$")
    public void systemShowsGDPRMenuOnLeftPanel() {
        driver.expectPageToContainText("GDPR");
    }

    @When("^I click on GDPR in menu$")
    public void clickGDPR() {
        driver.click("#GDPR");
    }

    @Then("^GDPR page shows in right side of page$")
    public void pageShouldShowGDPRpage() {
        driver.expectURLToContain("/gdpr");
    }

    @And("^Trigger button should exist in page$")
    public void triggerButtonShouldExist() {
        driver.expectElementToExist("#triggerBtn");
    }
}
