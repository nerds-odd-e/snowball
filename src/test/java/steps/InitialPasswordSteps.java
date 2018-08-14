package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class InitialPasswordSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^Admin add a new contact \"([^\"]*)\" with email: \"([^\"]*)\"$")
    public void admin_add_a_new_contact_with_email(String name, String email) throws Throwable {
        site.addContactPage().addContact(email, "Japan", "Tokyo");
    }

    @Then("^An confirmation email is sent to \"([^\"]*)\" from: \"([^\"]*)\"$")
    public void an_confirmation_email_is_sent_to_from(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^\"([^\"]*)\" click the link in the email$")
    public void click_the_link_in_the_email(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String token = "123123123asbs";
        driver.visit("http://localhost:8060/massive_mailer/initial_password?token=" + token);
    }

    @When("^\"([^\"]*)\" set password to \"([^\"]*)\"$")
    public void set_password_to(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" can login with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void can_login_with_and(String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Admin add a new contact \"([^\"]*)\" with invalid email: \"([^\"]*)\"$")
    public void admin_add_a_new_contact_with_invalid_email(String name, String email) throws Throwable {
        site.addContactPage().addContact(email, "Japan", "Tokyo");
    }

    @Then("^Contact page show \"([^\"]*)\"$")
    public void contact_page_show(String errorMsg) throws Throwable {
        driver.pageShouldContain(errorMsg);
    }

    @Then("^Contact was not created$")
    public void contact_was_not_created() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Mail was not sent$")
    public void mail_was_not_sent() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Admin add a new contact Yang with email: \"([^\"]*)\"$")
    public void admin_add_a_new_contact_Yang_with_email(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Yang change the token in the url to \"([^\"]*)\" and access the new url$")
    public void yang_change_the_token_in_the_url_to_and_access_the_new_url(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" message is shown$")
    public void message_is_shown(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Yang cannot set password$")
    public void yang_cannot_set_password() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
