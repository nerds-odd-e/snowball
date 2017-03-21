package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class EmailStepdefs {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @Given("^Visit Send Mail Page$")
    public void visitSendMailPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^I Click Send Email$")
    public void clickSendEmail() throws Throwable {
        driver.click_button("send_button");
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
        driver.text_field("recipient", recipient);
    }

    @Given("^Email Subject is \"(.*)\"$")
    public void addEmailSubject(String content) throws Throwable {
        driver.text_field("subject", content);
    }

    @Given("^Email Content is \"(.*)\"$")
    public void addEmailContent(String content) throws Throwable {
        driver.text_field("content", content);
    }



}
