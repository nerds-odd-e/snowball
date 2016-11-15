package gradle.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.UiElement;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class EmailTrackingStepdefs {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @Given("^I sent an email to \"([^\"]*)\" with subject \"([^\"]*)\" and content \"([^\"]*)\"$")
    public void sendEmailAndStore(String recipient, String subject, String content) throws Throwable {
        driver.text_field("recipient", recipient);
        driver.text_field("subject", subject);
        driver.text_field("content", content);
        driver.click_button("send_button");
        TimeUnit.SECONDS.sleep(10);
    }

    @When("^Visit Email Tracking Page$")
    public void visitSendMailPage() throws Throwable {

    }

    @Then("^I should get an element \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iShouldGetAnElementWithMessage(String subject, String date) throws Throwable {
//        driver.expectElementWithIdToContainText("subject", subject);
    }

    @Given("^I send an email with subject \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iSendAnEmailWithSubjectTo(String subject, String to) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("^I should see the email with subject \"([^\"]*)\" in the list with the sent date$")
    public void i_should_see_the_email_with_subject_in_the_list_with_the_sent_date(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @When("^I click the email subject \"([^\"]*)\"$")
    public void i_click_the_email_subject(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @Then("^I should see a list of recipients including \"([^\"]*)\"$")
    public void i_should_see_a_list_of_recipients_including(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }
}
