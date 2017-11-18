package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class ListEmailStepDefs {

    private WebDriverWrapper emailListDriverViewer = WebDriverFactory.getDefaultDriver();
    TrackEmailSteps emailSteps = new TrackEmailSteps();

    @Given("^Terry sends an email$")
    public void terry_sends_an_email() throws Throwable {
        emailSteps.sendEmail("test@example.com","hello");
    }

    @When("^Terry clicks on the email track link$")
    public void terry_clicks_on_the_email_track_link() throws Throwable {
        emailListDriverViewer.findElementById("emailtracking").click();
    }

    @Then("^Terry can see the email list page$")
    public void terry_can_see_the_email_list_page() throws Throwable {
        emailListDriverViewer.isAtURL(CucumberConstant.EMAIL_TRACKING_URL);
    }

    @Given("^Terry send an email with subject \"([^\"]*)\"$")
    public void terry_send_an_email_with_subject_on(String subject) throws Throwable {
        emailSteps.sendEmail("test@example.com", subject);
    }

    @Then("^Terry should see the email with subject \"([^\"]*)\" in the list with date$")
    public void terry_should_see_the_email_with_subject_in_the_list_with_date(String subject) throws Throwable {
        emailListDriverViewer.pageShouldContain(subject);
    }
}
