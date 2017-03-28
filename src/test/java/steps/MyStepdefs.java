package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;


public class MyStepdefs {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    public String BASE_URL = "http://localhost:8070/massive_mailer/";

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\"$")
    public void addAContact(String email,String location) throws Throwable {
        addContact(BASE_URL  + "add_contact.jsp", email, location);
    }

    @And("^Page Should Contain \"([^\"]*)\"$")
    public void pageShouldContain(String text) throws Throwable {
        driver.pageShouldContain(text);
    }

    @Then("^Element \"([^\"]*)\" Should Contain \"([^\"]*)\"$")
    public void expectElementWithIdToContainText(String element, String text) throws Throwable {
        driver.expectElementWithIdToContainText(element, text);
    }

    @And("^Page Should Contain Exactly (\\d+) \"([^\"]*)\"$")
    public void pageShouldContainExactlyNElements(int count, String text) throws Throwable {
        driver.expectPageToContainExactlyNElements(text, count);
    }

    @Given("^\"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email) throws Throwable {
        addAContact(email,"");
    }

    @When("^Login with email \"([^\"]*)\"$")
    public void loginWithEmail(String email) throws Throwable{
        loginPage(BASE_URL + "game_login.jsp", email);
    }

    @Then("^Page should be redirected to \"([^\"]*)\"$")
    public void pageRedirectTo(String page) throws Throwable {
        driver.expectRedirect(page);
    }

    @And("^Contacts page should contain \"([^\"]*)\"$")
    public void contactsListPageShouldContain(String email) throws Throwable{
        driver.visit(BASE_URL + "contactlist.jsp");
        pageShouldContain(email);
    }

    @And("^Contacts page should contain exactly (\\d+) \"([^\"]*)\"$")
    public void contactsListPageShouldContain(int count, String email) throws Throwable{
        driver.visit(BASE_URL + "contactlist.jsp");
        pageShouldContainExactlyNElements(count, email);
    }

    private void addContact(String url, String email, String location) throws Throwable{
        driver.visit(url);
        driver.text_field("email", email);
        driver.text_field("location", location);
        driver.click_button("add_button");
    }

    @Given("^There is a contact \"([^\"]*)\"$")
    public void there_is_a_contact(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I change the location information of contact to be \"([^\"]*)\"$")
    public void i_change_the_location_information_of_contact_to_be(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^contact \"([^\"]*)\"'s locations should be \"([^\"]*)\"$")
    public void contact_s_locations_should_be(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    private void loginPage(String url, String email) throws Throwable{
        driver.visit(url);
        driver.text_field("email", email);
        driver.click_button("add_button");
    }
}
