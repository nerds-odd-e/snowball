package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MultipleEnrollmentSteps {
    @Given("^There is a course \"([^\"]*)\" already registered$")
    public void thereIsACourseAlreadyRegistered(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enroll participants to \"([^\"]*)\"$")
    public void iEnrollParticipantsTo(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enroll \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iEnrollTo(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^participant with correct information is enrolled to \"([^\"]*)\"$")
    public void participantWithCorrectInformationIsEnrolledTo(String arg0) throws Throwable {
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
