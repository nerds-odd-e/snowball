package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import steps.page.ImagePage;

import static steps.page.Notifications.getSentMailVisitCount;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrackEmailSteps {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    @Given("^I send an email to \"(.*)\"$")
    public void i_send_an_email_to_Terry(String recipient) throws Throwable {
        sendEmail(recipient, "Subject");
    }

    public void sendEmail(String recipient, String subject) {
        driver.visit(BASE_URL);
        driver.setTextField("recipient", recipient);
        driver.setTextField("subject", subject);
        driver.setTextField("content", "Hello!");

        driver.clickButton("send_button");
    }

    @When("^\"(.*)\" does not open the email$")
    public void terry_does_not_open_the_email(String receipient) throws Throwable {
    }

    @Then("^I should see that \"(.*)\" has not opened the email$")
    public void i_should_see_that_Terry_has_not_opened_the_email(String receipient) throws Throwable {
        int count = getSentMailVisitCount(receipient);

        assertThat(count, is(0));
    }

    @When("^\"(.*)\" open the email$")
    public void terry_open_the_email(String recipient) throws Throwable {
        ImagePage page = new ImagePage();

        page.load(recipient);
    }

    @Then("^I should see that \"(.*)\" has opened the email$")
    public void i_should_see_that_Terry_has_opened_the_email(String receipient) throws Throwable {
        int count = getSentMailVisitCount(receipient);

        assertThat(count, is(1));
    }
}
