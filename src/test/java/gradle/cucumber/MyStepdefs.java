package gradle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.SingleDrive;
import gradle.cucumber.driver.WebDriverWrapper;


public class MyStepdefs {

    private WebDriverWrapper driver = SingleDrive.getDriver();

    @When("^Add A Contact \"([^\"]*)\"$")
    public void addAContact(String email) throws Throwable {
        loginToPage("http://localhost:8070/massive_mailer/add_contact.jsp", email);
    }

    @Then("^I should get an alert dialog with message \"([^\"]*)\"$")
    public void iShouldGetAnAlertDialogWithMessage(String msg) throws Throwable {
        driver.expectAlert(msg);
    }

    @And("^Page Should Contain \"([^\"]*)\"$")
    public void pageShouldContain(String text) throws Throwable {
        driver.pageShouldContain(text);
    }

    @Given("^\"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email) throws Throwable {
        addAContact(email);
        iShouldGetAnAlertDialogWithMessage("Add contact successfully");
    }

    @When("^Login with email \"([^\"]*)\"$")
    public void loginWithEmail(String email) throws Throwable{
        loginToPage("http://localhost:8070/massive_mailer/game_login.jsp", email);
    }

    @Then("^Page should be redirected to \"([^\"]*)\"$")
    public void pageIsAt(String page) throws Throwable{
        driver.isAtURL("http://localhost:8070/massive_mailer/" + page + ".jsp");
    }

    @And("^Contacts page should contain \"([^\"]*)\"$")
    public void contactsListPageShouldContain(String email) throws Throwable{
        driver.visit("http://localhost:8070/massive_mailer/contactlist.jsp");
        pageShouldContain(email);
    }

    private void loginToPage(String url, String email) throws Throwable{
        driver.visit(url);
        driver.text_field("email", email);
        driver.click_button("add_button");
    }
}
