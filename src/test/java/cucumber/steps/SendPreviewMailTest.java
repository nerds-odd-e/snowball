package cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.List;


public class SendPreviewMailTest {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private static final String A_COURSE = "A course";

    @Given("^there is a course starting from \"([^\"]*)\"$")
    public void there_is_a_course_starting_from(String date) {
        EventTests eventTests = new EventTests();
        eventTests.addCourseWithCountryAndCity(A_COURSE, "Singapore","Singapore", date);
    }

    @Given("^there is a student with information \"([^\"]*)\" loaded for this course$")
    public void there_are_students_with_email_loaded_for_this_course(String information) {
        if (information.isEmpty()) return;
        site.enrollParticipantPage().enrollParticipants(A_COURSE, information);
    }

    @When("^i send preview email for this course$")
    public void i_send_preview_email_for_this_course() {
        site.courseListPage().sendPreviewEmailFor(A_COURSE);
    }

    @Then("^\"([^\"]*)\" should receive the pre-course email$")
    public void should_receive_the_pre_course_email(String email) {
        site.ExpectNoEmailTo(email);
    }

    @When("^i send precourse email for this course$")
    public void i_send_precourse_email_for_this_course() {
        site.courseListPage().sendPrecourseEmailFor(A_COURSE);
        driver.expectPageToContainText("Successfully");
    }

    @Then("^\"([^\"]*)\" shouldn't receive any email$")
    public void shouldn_t_receive_email(String email) {
        site.ExpectEmailTo(email);
    }

    @Then("^all participants below should receive the pre-course email$")
    public void allParticipantsBelowShouldReceiveThePreCourseEmail(List<String> emails) {
        emails.forEach(site::ExpectEmailTo);
    }
}
