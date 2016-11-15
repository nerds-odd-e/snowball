package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

public class EmailTrackingStepdefs {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @Given("^I sent an email and store \"([^\"]*)\"$")
    public void sendEmailAndStore(String subject) throws Throwable {
//        driver.text_field("recipient", recipient);
//        driver.findElementById("subject1").
    }

    @When("^Visit Email Tracking Page$")
    public void visitSendMailPage() throws Throwable {
        driver.visit(BASE_URL + "email_tracking.jsp");
    }

    @Then("^I should get an element with message \"([^\"]*)\"$")
    public void iShouldGetAnElementWithMessage(String msg) throws Throwable {
        driver.expectElementWithIdToContainText("email_result", msg);
    }

}
