package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.stream.Collectors;

public class MultipleEnrollmentSteps {

    private MassiveMailerSite site = new MassiveMailerSite();

    @When("^I enroll participants to \"([^\"]*)\"$")
    public void iEnrollParticipantsTo(String courseName, DataTable participantsData) throws Throwable {
        String participants = participantsData.asList(String.class).stream().collect(Collectors.joining());
        site.enrollParticipantPage().addStudentsTo(courseName, participants);
    }

    @When("^I enroll \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iEnrollTo(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" is enrolled to \"([^\"]*)\"$")
    public void isEnrolled(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" is not enrolled to \"([^\"]*)\"$")
    public void isNotEnrolled(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
