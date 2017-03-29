package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import steps.page.ImagePage;

import static steps.page.Notifications.getNotificationDetailCount;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TrackEmailWithMultipleRecipientsSteps {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    @Given("^I send an email to multiple recipients$")
    public void i_send_an_email_to_multiple_recipients() throws Throwable {
        driver.visit(BASE_URL);
        driver.setTextField("recipient", "recipient@odd-e.com;recipient2@odd-e.com");
        driver.setTextField("subject", "Subject");
        driver.setTextField("content", "Hello!");

        driver.clickButton("send_button");
    }

    @When("^no recipient opens their email$")
    public void no_recipient_opens_their_email() throws Throwable {
    }

    @When("^all recipients opens their email$")
    public void all_recipients_opens_their_email() throws Throwable {
        ImagePage page = new ImagePage();
        page.load("recipient@odd-e.com");
        page.load("recipient2@odd-e.com");
    }

    @When("^one recipient opens their email$")
    public void one_recipient_opens_their_email() throws Throwable {
        ImagePage page = new ImagePage();
        page.load("recipient@odd-e.com");
    }

    @When("^no recipients did not open their email$")
    public void no_recipients_did_not_open_their_email() throws Throwable {
    }

    @When("^all recipients did not open their email$")
    public void all_recipients_did_not_open_their_email() throws Throwable {
    }

    @When("^all other recipients did not open their email$")
    public void all_other_recipients_did_not_open_their_email() throws Throwable {
    }

    @Then("^I must see that no recipient has opened their email$")
    public void i_must_see_that_no_recipient_has_opened_their_email() throws Throwable {
        assertThat(getNotificationDetailCount("recipient@odd-e.com"), is(0));
        assertThat(getNotificationDetailCount("recipient2@odd-e.com"), is(0));
    }


    @Then("^I must see that all recipients has opened their email$")
    public void i_must_see_that_all_recipients_has_opened_their_email() throws Throwable {
        assertThat(getNotificationDetailCount("recipient@odd-e.com"), is(1));
        assertThat(getNotificationDetailCount("recipient2@odd-e.com"), is(1));
    }

    @Then("^I must see that one recipient has opened their email$")
    public void i_must_see_that_one_recipient_has_opened_their_email() throws Throwable {
        assertEquals(1, getNotificationDetailCount("recipient@odd-e.com"));
        assertEquals(0, getNotificationDetailCount("recipient2@odd-e.com"));
    }
}
