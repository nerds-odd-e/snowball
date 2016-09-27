package gradle.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.SingleDrive;
import gradle.cucumber.driver.WebDriverWrapper;


public class MyStepdefs {

    private WebDriverWrapper driver = SingleDrive.getDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @When("^Add A Contact \"([^\"]*)\"$")
    public void addAContact(String email) throws Throwable {
        loginToPage(BASE_URL  + "add_contact.jsp", email);
    }

    @Then("^I should get an alert dialog with message \"([^\"]*)\"$")
    public void iShouldGetAnAlertDialogWithMessage(String msg) throws Throwable {
        driver.expectAlert(msg);
    }

    @And("^Page Should Contain \"([^\"]*)\"$")
    public void pageShouldContain(String text) throws Throwable {
        driver.pageShouldContain(text);
    }

    @And("^Page Should Contain Exactly (\\d+) \"([^\"]*)\"$")
    public void pageShouldContainExactlyNElements(int count, String text) throws Throwable {
        driver.expectPageToContainExactlyNElements(text, count);
    }

    @Given("^\"([^\"]*)\" is a contact already$")
    public void is_a_contact_already(String email) throws Throwable {
        addAContact(email);
        iShouldGetAnAlertDialogWithMessage("Add contact successfully");
    }

    @When("^Login with email \"([^\"]*)\"$")
    public void loginWithEmail(String email) throws Throwable{
        loginToPage(BASE_URL + "game_login.jsp", email);
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

    private void loginToPage(String url, String email) throws Throwable{
        driver.visit(url);
        driver.text_field("email", email);
        driver.click_button("add_button");
    }

}
