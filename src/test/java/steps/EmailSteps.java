package steps;

import com.odde.massivemailer.model.SentMail;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static steps.page.Notifications.getSentMailVisitCount;

public class EmailSteps {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @Given("^Visit Send Mail Page$")
    public void visitSendMailPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^I Click Send Email$")
    public void clickSendEmail() throws Throwable {
        driver.clickButton("send_button");
    }
    @Then("^I should get an element with message \"([^\"]*)\"$")
    public void iShouldGetAnElementWithMessage(String msg) throws Throwable {
        driver.expectElementWithIdToContainText("email_result", msg);
    }

    @Given("^Default Email with Recipients \"([^\"]*)\"$")
    public void defaultEmailWithRecipients(String recipients) throws Throwable {
        addEmailRecipient(recipients);
        addEmailSubject("default subject");
        addEmailContent("default content");
    }

    @Given("^Add Email Recipient \"(.*)\"$")
    public void addEmailRecipient(String recipient) throws Throwable {
        driver.setTextField("recipient", recipient);
    }

    @Given("^Email Subject is \"(.*)\"$")
    public void addEmailSubject(String content) throws Throwable {
        driver.setTextField("subject", content);
    }

    @Given("^Email Content is \"(.*)\"$")
    public void addEmailContent(String content) throws Throwable {
        driver.setTextField("content", content);
    }

    @Then("It should not send out emails")
    public void shouldNotSendOutEmails() {
        assertThat(SentMail.count(), is(0L));
    }

    @Then("^It should send (\\d+) emails$")
    public void it_should_send(int expected_email_count) throws Throwable {
        assertThat(SentMail.count(), is((long)expected_email_count));
    }

}
