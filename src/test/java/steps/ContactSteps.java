package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.*;


public class ContactSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\"$")
    public void addAContact(String email, String location) {
        site.addContactPage().addContactWithLocationString(email, location);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\" for \"([^\"]*)\", \"([^\"]*)\" from \"([^\"]*)\"$")
    public void addAContactWithAllInputs(String email, String country, String city, String name, String lastName, String company) {
        site.addContactPage().addContactWithAllInput(email, country, city, name, lastName, company);
    }

    @Given("^I am on the new contact page$")
    public void i_am_on_the_new_contact_page() {
        site.visit("add_contact.jsp");
        driver.pageShouldContain("Add Contact");
    }

    @Then("^I can see the element for \"([^\"]*)\"$")
    public void i_can_see_the_element(String arg1) {
        driver.findElementById(arg1);
    }

    @Then("^I should get an element with message email sent: \"([^\"]*)\"$")
    public void i_should_get_an_element_with_message_email_sent(String emailSent) {
        driver.pageShouldContain("email sent: " + emailSent);
    }

    @Then("^it should not create a new contact \"([^\"]*)\"$")
    public void itShouldNotCreateANewContact(String anotherEmail) {
        site.visit("contactlist.jsp");
        driver.pageShouldContain("user5@odd-e.com");
        String bodyText = driver.getBodyText();
        assertFalse(bodyText.contains(anotherEmail));
    }

    @And("^Page Should Success")
    public void pageShouldSuccess() {
        assertTrue(driver.getCurrentUrl().contains("status=success"));
    }

    @Then("^Element \"([^\"]*)\" Should Contain \"([^\"]*)\"$")
    public void expectElementWithIdToContainText(String element, String text) {
        driver.expectElementWithIdToContainText(element, text);
    }

    @And("^Page Should Contain Exactly (\\d+) \"([^\"]*)\"$")
    public void pageShouldContainExactlyNElements(int count, String text) {
        driver.expectPageToContainExactlyNElements(text, count);
    }

    @Then("^Page should be redirected to \"([^\"]*)\"$")
    public void pageRedirectTo(String page) {
        driver.expectRedirect(page);
    }

    @And("^Contacts page should contain \"([^\"]*)\"$")
    public void contactsListPageShouldContain(String email) {
        site.visit("contactlist.jsp");
        driver.pageShouldContain(email);
    }

    @And("^Contacts page should contain exactly (\\d+) \"([^\"]*)\"$")
    public void contactsListPageShouldContain(int count, String email) {
        site.visit("contactlist.jsp");
        pageShouldContainExactlyNElements(count, email);
    }

    @Given("^There is a contact \"([^\"]*)\"$")
    public void there_is_a_contact(String email) {
        site.visit("contactlist.jsp");
        driver.pageShouldContain(email);
    }

    @When("^I change the location information of contact to be \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_change_the_location_information_of_contact_to_be(String country, String city) {
        site.visit("contactlist.jsp");
        driver.clickButton("edit_button");
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
        driver.clickButton("save_button");
    }

    @Then("^contact \"([^\"]*)\"'s locations should be \"([^\"]*)\"$")
    public void contact_s_locations_should_be(String email, String location) {
        driver.pageShouldContain(email);
        driver.pageShouldContain(location);
    }

    @When("^I open edit contact page for contact \"([^\"]*)\"$")
    public void iOpenEditContactPageForContact(String email) {
        site.visit("contactlist.jsp");
        driver.clickButton("edit_button");
    }

    @Given("^Contact for \"([^\"]*)\" which is not existing in the system$")
    public void contactForWhichIsNotExistingInTheSystem(String email) {
        assertNotNull(email);
        assertFalse(email.isEmpty());
    }

    @When("^I add the contact whose name is \"([^\"]*)\" and lastname is \"([^\"]*)\" and company is \"([^\"]*)\" and email is \"([^\"]*)\" and location is \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iAddTheContactWhoseNameIsAndLastnameIsAndCompanyIsAndEmailIsAndLocationIsAnd(String name, String lastname, String company, String email, String country, String city) {
        assertNotNull(name);
        assertNotNull(lastname);
        assertNotNull(company);
        assertNotNull(country);
        assertNotNull(email);
        assertNotNull(city);

        site.addContactPage().addContactWithAllInput(email, country, city, name, lastname, company);
    }

    @Then("^Contact \"([^\"]*)\" record is created$")
    public void contactRecordIsCreated(String email) {
        site.visit("contactlist.jsp");
        driver.pageShouldContain(email);
    }
}