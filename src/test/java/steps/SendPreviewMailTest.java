package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class SendPreviewMailTest {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    public static final String A_COURSE = "A course";

    private void addCoursesAtTokyo(int offsetDate) throws Throwable {
        LocalDateTime d = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        d.plusMonths(offsetDate);

        site.visit("add_course.jsp");
        driver.setTextField("coursename", A_COURSE);
        driver.setTextField("duration", "30");
        driver.setDropdownValue("location", "Tokyo");
        driver.setTextField("startdate", d.format(f));
        driver.setTextField("address", "odd-e");
        driver.setTextField("coursedetails", "csd");
        driver.setTextField("instructor", "terry");

        driver.clickButton("save_button");
    }

    @Given("^there is a course starting from \"([^\"]*)\"$")
    public void there_is_a_course_starting_from(String date) throws Throwable {
        EventTests eventTests = new EventTests();
        eventTests.addCourseWithCountryAndCity(A_COURSE, "Singapore","Singapore", date);
    }

    @Given("^there are students with email \"([^\"]*)\" loaded for this course$")
    public void there_are_students_with_email_loaded_for_this_course(String email) throws Throwable {
        site.enrollParticipantPage().addStudentTo(A_COURSE, email);
    }

    @When("^i send preview email for this course$")
    public void i_send_preview_email_for_this_course() throws Throwable {
        site.courseListPage().sendPreviewEmailFor(A_COURSE);
    }

    @Then("^\"([^\"]*)\" should receive the pre-course email$")
    public void should_receive_the_pre_course_email(String arg1) throws Throwable {
    }

    @When("^i send precourse email for this course$")
    public void i_send_precourse_email_for_this_course() throws Throwable {
        site.courseListPage().sendPrecourseEmailFor(A_COURSE);
    }

    @Given("^There is a contact \"([^\"]*)\" at Tokyo$")
    public void there_is_a_contact_at_Tokyo(String email) throws Throwable {
        ContactSteps contactTests = new ContactSteps();
        contactTests.addAContact(email, "Japan", "Tokyo");
    }

    @Given("^there are two courses at Tokyo$")
    public void there_are_two_courses_at_Tokyo() throws Throwable {
        addCoursesAtTokyo(1);
        addCoursesAtTokyo(30);
    }

    @When("^I trigger the sending twice$")
    public void i_trigger_the_sending_twice() throws Throwable {
        i_trigger_the_sending_once();
        i_trigger_the_sending_once();
    }

    @When("^I trigger the sending once$")
    public void i_trigger_the_sending_once() throws Throwable {
        site.visit( "course_list.jsp");
        driver.clickButton("send_button");
        driver.waitforElement("message");
    }

    @Given("^there is a course at Tokyo$")
    public void there_is_a_course_at_Tokyo() throws Throwable {
        addCoursesAtTokyo(1);
    }

    @Then("^\"([^\"]*)\" shouldn't receive email$")
    public void shouldn_t_receive_email(String arg1) throws Throwable {
        driver.expectElementWithIdToContainText("message", "0 emails contain 0 events sent.");
    }
}
