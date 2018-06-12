package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertTrue;


public class ContactSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\"$")
    public void addAContact(String email,String location) throws Throwable {
        site.addContactPage().addContactWithLocationString(email, location);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\"$")
    public void addAContact(String email,String country, String city) throws Throwable {
        site.addContactPage().addContact(email, country, city);
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

    @Given("^\"([^\"]*)\" which in \"([^\"]*)\" and \"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email, String country, String city) throws Throwable {
        addAContact(email,country,city);
    }

    @Then("^Page should be redirected to \"([^\"]*)\"$")
    public void pageRedirectTo(String page) throws Throwable {
        driver.expectRedirect(page);
    }

    @And("^Contacts page should contain \"([^\"]*)\"$")
    public void contactsListPageShouldContain(String email) throws Throwable{
        site.visit("contactlist.jsp");
        pageShouldContain(email);
    }

    @And("^Contacts page should contain exactly (\\d+) \"([^\"]*)\"$")
    public void contactsListPageShouldContain(int count, String email) throws Throwable{
        site.visit("contactlist.jsp");
        pageShouldContainExactlyNElements(count, email);
    }

    @Given("^There is a contact \"([^\"]*)\"$")
    public void there_is_a_contact(String email) throws Throwable {
        site.visit( "contactlist.jsp");
        pageShouldContain(email);
    }

    @When("^I change the location information of contact to be \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_change_the_location_information_of_contact_to_be(String country, String city) throws Throwable {
        site.visit( "contactlist.jsp");
        driver.clickButton("edit_button");
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
        driver.clickButton("save_button");
    }

    @Then("^contact \"([^\"]*)\"'s locations should be \"([^\"]*)\"$")
    public void contact_s_locations_should_be(String email, String location) throws Throwable {
        pageShouldContain(email);
        pageShouldContain(location);
    }
}
