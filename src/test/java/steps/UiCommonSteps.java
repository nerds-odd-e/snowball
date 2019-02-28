package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiCommonSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^topページが表示されている$")
    public void topページが表示されている() throws Throwable {
        site.visit("ui_common.jsp");
    }

    @When("^Update Adviceのリンクをクリックする$")
    public void updateAdviceのリンクをクリックする() throws Throwable {
        driver.findElementById("update_advice").click();
    }

    @When("^StartTestのリンクをクリックする$")
    public void starttestのリンクをクリックする() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^カテゴリ選択画面が表示される$")
    public void カテゴリ選択画面が表示される() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^カテゴリのチェックボックスは全て選択されている$")
    public void カテゴリのチェックボックスは全て選択されている() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Update Adviceが表示される$")
    public void updateAdviceが表示される() throws Throwable {
        assertTrue(driver.getBodyHTML().contains("nav navbar-nav side-nav"));
        assertEquals("Update Advice", driver.getCurrentTitle());
    }
}
