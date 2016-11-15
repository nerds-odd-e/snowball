package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

/**
 *
 * step definition class for the sent email page
 *  this will list all the email with subject
 *
 *  ------------------------------
 *   email key | Subject   | time stamp |
 *   1         | promotion | 21:00:00 PM|
 */
public class ListEmailStepDefs {

    private WebDriverWrapper emailListDriverViewer = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = CucumberConstant.BASE_URL;

    /**
     *
     * @throws Throwable
     */
    @Given("^Terry is logged in with \"([^\"]*)\"$")
    public void terry_is_logged_in_with(String user) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        loginToPage(BASE_URL,user);
    }

    @When("^he clicks on the email track link on home page$")
    public void he_clicks_on_the_email_track_link() throws Throwable {
        System.out.println("inside when");
    }

    @Then("^he can see the email list page$")
    public void he_can_see_the_email_list_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }
    private void loginToPage(String url, String email) throws Throwable{
        emailListDriverViewer.visit(url);
        emailListDriverViewer.click_button("Email list");
    }


    @Given("^Terry sends an email$")
    public void terry_sends_an_email() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
       System.out.print(" entering in ");
    }

    @When("^Terry is on the home page$")
    public void terry_is_on_the_home_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print(" entering in ");
    }

    @When("^Terry clicks on the email track link$")
    public void terry_clicks_on_the_email_track_link() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print(" entering in ");
    }

    @Then("^Terry can see the email list page$")
    public void terry_can_see_the_email_list_page() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print(" entering in ");
    }

    @Given("^Terry does not send and email$")
    public void terry_does_not_send_and_email() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print(" entering in ");
    }

    @Then("^Terry sees the message \"([^\"]*)\"$")
    public void terry_sees_the_message(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        System.out.print(" entering in ");
    }



}
