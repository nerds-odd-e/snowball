package steps;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.odde.massivemailer.model.ContactPerson;
import org.junit.Assert;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import steps.page.CourseListPage;
import steps.page.EnrollParticipantPage;


public class SendPreviewMailTest {
    public static final String A_COURSE = "A course";

   /* private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String adminEmail, studentEmail;*/



    @Given("^there is a course starting from \"([^\"]*)\"$")
    public void there_is_a_course_starting_from(String date) throws Throwable {
        EventTests eventTests = new EventTests();
        eventTests.addCourse(A_COURSE, "Singapore", date);
    }

    @Given("^there are students with email \"([^\"]*)\" loaded for this course$")
    public void there_are_students_with_email_loaded_for_this_course(String email) throws Throwable {
        new EnrollParticipantPage().addStudentTo(A_COURSE, email);
    }

    @When("^i send preview email for this course$")
    public void i_send_preview_email_for_this_course() throws Throwable {
        new CourseListPage().sendPreviewEmailFor(A_COURSE);
    }

    @Then("^\"([^\"]*)\" should receive the pre-course email$")
    public void should_receive_the_pre_course_email(String arg1) throws Throwable {
    }

    @When("^i send precourse email for this course$")
    public void i_send_precourse_email_for_this_course() throws Throwable {
        new CourseListPage().sendPrecourseEmailFor(A_COURSE);
    }

}
