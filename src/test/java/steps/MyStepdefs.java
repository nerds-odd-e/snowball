package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import static org.junit.Assert.assertTrue;


public class MyStepdefs {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    public String BASE_URL = "http://localhost:8070/massive_mailer/";

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\"$")
    public void addAContact(String email,String location) throws Throwable {
        addContact(BASE_URL  + "add_contact.jsp", email, location);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\"$")
    public void addAContact(String email,String country, String city) throws Throwable {
        addContact(BASE_URL  + "add_contact.jsp", email, country, city);
    }

    @And("^Page Should Contain \"([^\"]*)\"$")
    public void pageShouldContain(String text) throws Throwable {
        driver.pageShouldContain(text);
    }

    @And("^Page Should Success")
    public void pageShouldSuccess() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("status=success"));
    }

    @And("^Page Should Fail$")
    public void pageShouldFail() throws Throwable {
        assertTrue(driver.getCurrentUrl().contains("status=failed"));
    }

    @Then("^Element \"([^\"]*)\" Should Contain \"([^\"]*)\"$")
    public void expectElementWithIdToContainText(String element, String text) throws Throwable {
        driver.expectElementWithIdToContainText(element, text);
    }

    @And("^Page Should Contain Exactly (\\d+) \"([^\"]*)\"$")
    public void pageShouldContainExactlyNElements(int count, String text) throws Throwable {
        driver.expectPageToContainExactlyNElements(text, count);
    }

    @Given("^\"([^\"]*)\" which in \"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email, String location) throws Throwable {
        addAContact(email,location);
    }

    @Given("^\"([^\"]*)\" which in \"([^\"]*)\" and \"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email, String country, String city) throws Throwable {
        addAContact(email,country,city);
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
        driver.setTextField("email", email);
        driver.setDropdownValue("location", location);
        driver.clickButton("add_button");
    }

    private void addContact(String url, String email, String country, String city) throws Throwable{
        driver.visit(url);
        driver.setTextField("email", email);
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
        driver.clickButton("add_button");
    }

    @Given("^There is a contact \"([^\"]*)\"$")
    public void there_is_a_contact(String email) throws Throwable {
        driver.visit(BASE_URL + "contactlist.jsp");
        pageShouldContain(email);
    }

    @When("^I change the location information of contact to be \"([^\"]*)\"$")
    public void i_change_the_location_information_of_contact_to_be(String location) throws Throwable {
        driver.visit(BASE_URL + "contactlist.jsp");
        driver.clickButton("edit_button");
        driver.setDropdownValue("location", location);
        driver.clickButton("save_button");
    }

    @Then("^contact \"([^\"]*)\"'s locations should be \"([^\"]*)\"$")
    public void contact_s_locations_should_be(String email, String location) throws Throwable {
        pageShouldContain(email);
        pageShouldContain(location);
    }

    private void loginPage(String url, String email) throws Throwable{
        driver.visit(url);
        driver.setTextField("email", email);
        driver.clickButton("add_button");
    }
}
