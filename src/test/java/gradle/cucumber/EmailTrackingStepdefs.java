package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

public class EmailTrackingStepdefs {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

//    public String mSubject = null;

    @Given("^I sent an email and store \"([^\"]*)\"$")
    public void sendEmailAndStore(String subject) throws Throwable {
//        driver.text_field("recipient", recipient);
//        driver.findElementById("subject1").
//        mSubject = subject;
    }

    @When("^Visit Email Tracking Page$")
    public void visitSendMailPage() throws Throwable {
        driver.visit(BASE_URL + "email_tracking.jsp");
//        driver.findElementById("subject1")
    }

    @Then("^I should get an element \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iShouldGetAnElementWithMessage(String subject, String date) throws Throwable {
//        driver.expectElementWithIdToContainText("email_result", msg);
    }

}
