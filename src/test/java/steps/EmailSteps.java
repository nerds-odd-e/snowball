package steps;

import com.odde.snowball.model.SentMail;
import com.odde.snowball.model.Template;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import static com.odde.snowball.model.base.Repository.repo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmailSteps {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^Visit Send Mail Page$")
    public void visitSendMailPage() {
        site.visit("admin/sendemail.jsp");
    }

    @When("^I Click Send Email$")
    public void clickSendEmail() {
        driver.click("#send_button");
    }

    @Then("^I should get an element with message \"([^\"]*)\"$")
    public void iShouldGetAnElementWithMessage(String msg) {
        driver.expectElementToContainText("#email_result", msg);
    }

    @Given("^Default Email with Recipients \"([^\"]*)\"$")
    public void defaultEmailWithRecipients(String recipients) {
        addEmailRecipient(recipients);
        addEmailSubject("default subject");
        addEmailContent("default content");
    }

    @Given("^Add Email Recipient \"(.*)\"$")
    public void addEmailRecipient(String recipient) {
        driver.setTextField("recipient", recipient);
    }

    @Given("^Email Subject is \"(.*)\"$")
    public void addEmailSubject(String content) {
        driver.setTextField("subject", content);
    }

    @Given("^Email Content is \"(.*)\"$")
    public void addEmailContent(String content) {
        driver.setTextField("content", content);
    }

    @Then("It should not send out emails")
    public void shouldNotSendOutEmails() {
        assertThat(repo(SentMail.class).count(), is(0L));
    }

    @Then("^It should send (\\d+) emails$")
    public void it_should_send(int expected_email_count) {
        assertThat(repo(SentMail.class).count(), is((long)expected_email_count));
    }

    @Given("^there are some existing templates$")
    public void thereAreSomeExistingTemplates() {
        new Template("Default Template 1", "Greeting {FirstName}", "Hi, {FirstName} {LastName} from {Company}").save();
        new Template("RTA Default Template", "Greeting {FirstName}", "Hi, {FirstName} {LastName} from {Company}").save();
        new Template("Pre-course Template", "Greeting {FirstName}", "Hi, {FirstName} {LastName} from {Company}").save();
    }
}
