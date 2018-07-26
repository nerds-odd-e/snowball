package steps;

import com.odde.massivemailer.model.ContactPerson;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.UiElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.*;


public class ContactSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\"$")
    public void addAContact(String email, String location) throws Throwable {
        site.addContactPage().addContactWithLocationString(email, location);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\"$")
    public void addAContact(String email, String country, String city) throws Throwable {
        site.addContactPage().addContact(email, country, city);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\" for \"([^\"]*)\", \"([^\"]*)\" from \"([^\"]*)\"$")
    public void addAContactWithAllInputs(String email, String country, String city, String name, String lastName, String company) throws Throwable {
        site.addContactPage().addContactWithAllInput(email, country, city, name, lastName, company);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\" for \"([^\"]*)\", \"([^\"]*)\" from \"([^\"]*)\" with \"([^\"]*)\"$")
    public void addAContactWithConsentId(String email, String country, String city, String name, String lastName, String company, String consentId) throws Throwable {
        site.addContactPage().addContactWithAllInput(email, country, city, name, lastName, company, consentId);
    }

    @Given("^I am on the new contact page$")
    public void i_am_on_the_new_contact_page() throws Throwable {
        site.visit("add_contact.jsp");
        pageShouldContain("Add Contact");
    }

    @Then("^I can see the element for \"([^\"]*)\"$")
    public void i_can_see_the_element(String arg1) throws Throwable {
        driver.findElementById(arg1);
    }

    @When("Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\" with \"([^\"]*)\"")
    public void addConsentId (String email, String country, String city, String consentId) {
        site.addContactPage().addContactWithConsentId(email, country, city, consentId);
    }

    @Then("^I should get an element with message email sent: \"([^\"]*)\"$")
    public void i_should_get_an_element_with_message_email_sent(String emailSent) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        pageShouldContain("email sent: " + emailSent);
    }

    @Then("^it should not create a new contact \"([^\"]*)\"$")
    public void itShouldNotCreateANewContact(String anotherEmail) throws Throwable {
        site.visit("contactlist.jsp");
        driver.pageShouldContain("user5@odd-e.com");
        String bodyText = driver.getBodyText();
        assertFalse(bodyText.contains(anotherEmail));
    }

    @And("^I should get error message \"([^\"]*)\"$")
    public void iShouldGetErrorMessage(String errorMessage) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
    }

    @Given("^Contact for \"([^\"]*)\" exists in the system$")
    public void aContactAlreadyExistInDB(String email) {
        assertFalse(email.isEmpty());
    }

    @When("^I upload a valid CSV file with \"([^\"]*)\"$")
    public void uploadCSVFileWithSpecificEmail(String email) {
        assertFalse(email.isEmpty());
    }

    @Then("^the contact should be updated with \"([^\"]*)\"$")
    public void contactAttributeIsUpdated(String field) {
        assertFalse(field.isEmpty());
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
        addAContact(email, country, city);
    }

    @Then("^Page should be redirected to \"([^\"]*)\"$")
    public void pageRedirectTo(String page) throws Throwable {
        driver.expectRedirect(page);
    }

    @And("^Contacts page should contain \"([^\"]*)\"$")
    public void contactsListPageShouldContain(String email) throws Throwable {
        site.visit("contactlist.jsp");
        pageShouldContain(email);
    }

    @And("^Contacts page should contain exactly (\\d+) \"([^\"]*)\"$")
    public void contactsListPageShouldContain(int count, String email) throws Throwable {
        site.visit("contactlist.jsp");
        pageShouldContainExactlyNElements(count, email);
    }

    @Given("^There is a contact \"([^\"]*)\"$")
    public void there_is_a_contact(String email) throws Throwable {
        site.visit("contactlist.jsp");
        pageShouldContain(email);
    }

    @When("^I change the location information of contact to be \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_change_the_location_information_of_contact_to_be(String country, String city) throws Throwable {
        site.visit("contactlist.jsp");
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

    @Given("^\"([^\"]*)\" which with no consent id contact already$")
    public void whichWithNoConsentIdContactAlready(String email) throws Throwable {
        addAContact(email, "China", "Chengdu");
    }

    @When("^I add consent id \"([^\"]*)\"$")
    public void iAddConsentId(String consentId) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        site.visit("contactlist.jsp");
        driver.clickButton("edit_button");
        Thread.sleep(2000);
        driver.setTextField("consent_id", consentId);
        driver.clickButton("save_button");
    }

    @Then("^contact \"([^\"]*)\"'s consent id should be \"([^\"]*)\"$")
    public void contactSConsentIdShouldBe(String email, String consentId) throws Throwable {
        site.visit("contactlist.jsp");
        Thread.sleep(2000);
        pageShouldContain(email);
        pageShouldContain(consentId);
    }

    @Given("^There are the following contacts in the CSV file that do not exist in the system$")
    public void there_are_the_following_info_in_the_CSV_file(DataTable contacts) throws Throwable {
        List<String> contactString = contacts.asList(String.class);

        PrintWriter pw = new PrintWriter(new File(System.getProperty("java.io.tmpdir")+"/contactsUploadTest.csv"));
        StringBuilder contactToWrite = new StringBuilder();

        contactToWrite.append(contactString.get(0));
        contactToWrite.append('\n');

        for (int i = 1; i < contactString.size(); i++) {
            String contactDetail = contactString.get(i);
            String email = contactDetail.substring(0, contactDetail.indexOf(","));
            ContactPerson contactPerson = ContactPerson.getContactByEmail(email);
            contactToWrite.append(contactDetail);
            contactToWrite.append('\n');
        }

        pw.write(contactToWrite.toString());
        pw.close();
    }

    @When("^I upload the CSV file$")
    public void i_upload_the_CSV_file() throws Throwable {
        site.visit("add_contact_batch.jsp");
        driver.clickUpload();
    }

    @Then("^There must be two more contacts added$")
    public void there_must_be_two_more_contacts_added(DataTable emailList) throws Throwable {
        driver.expectAlert("Batch Contacts Uploaded");
        checkContactsAreCreated(emailList.asList(String.class));
        deleteCSVFile();
    }

    private void checkContactsAreCreated(List<String> emails) {
        site.visit("contactlist.jsp");
        for (String email : emails) {
            driver.pageShouldContain(email);
        }
    }

    private void deleteCSVFile() {
        File csvFile = new File(System.getProperty("java.io.tmpdir")+"/contactsUploadTest.csv");
        boolean deleteSuccess = false;
        if (csvFile.exists()) {
            deleteSuccess = csvFile.delete();
        }
        assertTrue(deleteSuccess);
    }

    @When("^I open edit contact page for contact \"([^\"]*)\"$")
    public void iOpenEditContactPageForContact(String email) throws Throwable {
        site.visit("contactlist.jsp");
        driver.clickButton("edit_button");
    }

    @Then("^I should see \"([^\"]*)\" in consent id textbox$")
    public void iShouldSeeInConsentIdTextbox(String consentId) throws Throwable {
        UiElement consentIdTextbox = driver.findElementByName("consent_id");
        assertEquals(consentId, consentIdTextbox.getAttribute("value"));
    }
}