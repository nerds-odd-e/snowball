package gradle.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MyStepdefs {

    private WebDriver driver = new FirefoxDriver();

    @When("^Add A Contact \"([^\"]*)\"$")
    public void addAContact(String arg0) throws Throwable {
        driver.get("http://localhost:8080/massive_mailer");
    }

    @Then("^I should get an alert dialog with message \"([^\"]*)\"$")
    public void iShouldGetAnAlertDialogWithMessage(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @And("^Page Should Contain \"([^\"]*)\"$")
    public void pageShouldContain(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

}
