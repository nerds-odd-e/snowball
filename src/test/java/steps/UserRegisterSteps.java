package steps;

import com.odde.snowball.model.SentMail;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.*;

public class UserRegisterSteps {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private SentMail sentMail = null;

    @Given("^\"([^\"]*)\" which in \"([^\"]*)\" and \"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email, String country, String city) {
        site.addContactPage().addContact(email, country, city);
    }

    @When("^Add A Contact \"([^\"]*)\" at \"([^\"]*)\" and \"([^\"]*)\"$")
    public void addAContact(String email, String country, String city) {
        site.addContactPage().addContact(email, country, city);
    }

    @Then("^Page Should Contain \"([^\"]*)\"$")
    public void pageShouldContain(String text) {
        driver.expectPageToContainText(text);
    }

    @And("^Page Should Fail$")
    public void pageShouldFail() {
        driver.expectURLToContain("status=fail");
    }

    @When("^Admin add a new contact \"([^\"]*)\" with email: \"([^\"]*)\"$")
    public void admin_add_a_new_contact_with_email(String name, String email) {
        site.addContactPage().addContact(email, "Japan", "Tokyo");
    }

    @Then("^An confirmation email is sent to \"([^\"]*)\" from: \"([^\"]*)\"$")
    public void an_confirmation_email_is_sent_to_from(String email, String fromAddres) {
        String expectUrl = site.baseUrl() + "initialPassword?token=";
        sentMail = SentMail.getSentMailBy(email);
        assertTrue(sentMail.getContent().contains(expectUrl));
    }

    @When("^\"([^\"]*)\" click the link in the email$")
    public void click_the_link_in_the_email(String arg1) {
        driver.visit(sentMail.getContent());
    }

    @When("set password to \"([^\"]*)\", password confirmation to \"([^\"]*)\"$")
    public void set_password_to(String password, String confirm) {
        site.initializePasswordPage().setPassword(password);
        site.initializePasswordPage().setPasswordConfirm(confirm);
        site.initializePasswordPage().submit();
    }

    @Then("^Show valid information$")
    public void show_valid_information() {
        driver.expectPageToContainText("Success!!");
    }

    @Then("^Show invalid information$")
    public void show_invalid_information() {
        driver.expectPageToContainText("Error!");
    }

    @When("^Admin add a new contact \"([^\"]*)\" with invalid email: \"([^\"]*)\"$")
    public void admin_add_a_new_contact_with_invalid_email(String name, String email) {
        site.addContactPage().addContact(email, "Japan", "Tokyo");
    }

    @Then("^Contact page show \"([^\"]*)\"$")
    public void contact_page_show(String errorMsg) {
        driver.expectPageToContainText(errorMsg);
    }

    @Then("^Contact was not created$")
    public void contact_was_not_created(String email) {

    }

    @Then("^\"([^\"]*)\" was not contained at Contact List Page$")
    public void was_not_contained_at_Contact_List_Page(String email) {
        driver.visit(site.baseUrl() + "contactlist.jsp");
        driver.expectNoElementToContainText("#contactTable", email);
    }

    @Then("^Mail was not sent$")
    public void mail_was_not_sent() {
    }

    @When("^Admin add a new contact \"([^\"]*)\" with valid email: \"([^\"]*)\"$")
    public void admin_add_a_new_contact_with_valid_email(String name, String email) {
        site.addContactPage().addContact(email, "Japan", "Tokyo");
    }

    @Then("^Contact list page show \"([^\"]*)\"$")
    public void contact_list_page_show(String email) {
        driver.expectElementToContainText("#contactTable", email);
    }

    @When("^\"([^\"]*)\" change the token in the url to \"([^\"]*)\" and access the new url$")
    public void change_the_token_in_the_url_to_and_access_the_new_url(String name, String token) {
        driver.visit(site.baseUrl() + "initialize_password.jsp?token=" + token);
        site.initializePasswordPage().setPassword("123");
        site.initializePasswordPage().setPasswordConfirm("123");
        site.initializePasswordPage().submit();
    }

    @Then("^\"([^\"]*)\" message is shown$")
    public void message_is_shown(String msg) {
        driver.expectPageToContainText(msg);
    }

    @Given("^There are the following contacts in the CSV file that do not exist in the system$")
    public void there_are_the_following_info_in_the_CSV_file(DataTable contacts) throws Throwable {
        List<String> contactString = contacts.asList(String.class);

        PrintWriter pw = new PrintWriter(new File(System.getProperty("java.io.tmpdir") + "/contactsUploadTest.csv"));
        StringBuilder contactToWrite = new StringBuilder();

        contactToWrite.append(contactString.get(0));
        contactToWrite.append('\n');

        for (int i = 1; i < contactString.size(); i++) {
            String contactDetail = contactString.get(i);
            contactToWrite.append(contactDetail);
            contactToWrite.append('\n');
        }

        pw.write(contactToWrite.toString());
        pw.close();
    }

    @When("^I upload the CSV file$")
    public void i_upload_the_CSV_file() {
        site.visit("add_contact_batch.jsp");
        driver.clickUpload("#batchFile", System.getProperty("java.io.tmpdir") + "/contactsUploadTest.csv");
    }

    @Then("^There must be two more contacts added$")
    public void there_must_be_two_more_contacts_added(DataTable emailList) {
        driver.expectAlert("Batch Contacts Uploaded");
        checkContactsAreCreated(emailList.asList(String.class));
        deleteCSVFile();
    }

    private void checkContactsAreCreated(List<String> emails) {
        site.visit("contactlist.jsp");
        for (String email : emails) {
            driver.expectPageToContainText(email);
        }
    }

    private void deleteCSVFile() {
        File csvFile = new File(System.getProperty("java.io.tmpdir") + "/contactsUploadTest.csv");
        boolean deleteSuccess = false;
        if (csvFile.exists()) {
            deleteSuccess = csvFile.delete();
        }
        assertTrue(deleteSuccess);
    }

}
