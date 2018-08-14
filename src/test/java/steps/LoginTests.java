package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertTrue;

public class LoginTests {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String login_url = site.baseUrl() + "login.jsp";

    @Given("^Visit Login Page$")
    public void visitLoginPage() throws Throwable {
        driver.visit(login_url);
    }

    @Given("^Fill form with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void fill_form_with_and(String arg1, String arg2) throws Throwable {
        driver.setTextField("email", arg1);
        driver.setTextField("password", arg2);
    }

    @When("^I click login button$")
    public void i_click_login_button() throws Throwable {
        driver.clickButton("login");
    }

    @Then("^Show course list of current user$")
    public void show_course_list_of_current_user() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should move to page with url \"([^\"]*)\" and message \"([^\"]*)\"$")
    public void i_should_move_to_page_with_url_and(String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
