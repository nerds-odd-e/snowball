package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static steps.site.pages.Notifications.getSentMailVisitCount;

public class TrackEmailSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^I send an email to \"(.*)\"$")
    public void i_send_an_email_to_Terry(String recipient) {
        sendEmail(recipient, "Subject");
    }

    public void sendEmail(String recipient, String subject) {
        site.visit("admin/sendemail.jsp");
        driver.setTextField("recipient", recipient);
        driver.setTextField("subject", subject);
        driver.setTextField("content", "Hello!");

        driver.clickButton("send_button");
    }

    @When("^\"(.*)\" does not open the email$")
    public void terry_does_not_open_the_email(String receipient) {
    }

    @Then("^I should see that \"(.*)\" has not opened the email$")
    public void i_should_see_that_Terry_has_not_opened_the_email(String receipient) {
        int count = getSentMailVisitCount(receipient);

        assertThat(count, is(0));
    }

    @When("^\"(.*)\" open the email$")
    public void terry_open_the_email(String recipient) {
        site.imagePage().load(recipient);
    }

    @Then("^I should see that \"(.*)\" has opened the email$")
    public void i_should_see_that_Terry_has_opened_the_email(String receipient) {
        int count = getSentMailVisitCount(receipient);

        assertThat(count, is(1));
    }
}
