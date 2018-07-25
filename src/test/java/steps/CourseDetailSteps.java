package steps;

import com.odde.massivemailer.model.Course;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.stream.Collectors;

public class CourseDetailSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String courseDetailUrl = site.baseUrl()+ "course_detail.jsp";

    @When("^I visit \"([^\"]*)\" detail page$")
    public void iVisitDetailPage(String courseName) throws Throwable {
        Course course = Course.getCourseByName(courseName);
        driver.visit(courseDetailUrl + "?id=" + course.getId().toString());
        driver.pageShouldContain(courseName);
    }

    @Then("^No participant is displayed in enrolled participant list$")
    public void noParticipantIsDisplayedInEnrolledParticipantList() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Two paricipants are enrolled to \"([^\"]*)\"$")
    public void twoParicipantsAreEnrolledTo(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Two participants are displayed in enrolled participant list$")
    public void twoParticipantsAreDisplayedInEnrolledParticipantList() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I enroll participants to \"([^\"]*)\" from course detail page$")
    public void iEnrollParticipantsToFromCourseDetailPage(String courseName, DataTable participantsData) throws Throwable {
        String participants = participantsData.asList(String.class).stream().collect(Collectors.joining("\n"));
        Course course = Course.getCourseByName(courseName);
        driver.visit(courseDetailUrl + "?id=" + course.getId().toString());
        driver.setTextField("participants", participants);
        driver.clickButton("add_button");
    }

    @Then("^participant with correct information apears on \"([^\"]*)\" course detail page$")
    public void participantWithCorrectInformationApearsOnCourseDetailPage(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Carry appears in the enroll form$")
    public void carryAppearsInTheEnrollForm() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
