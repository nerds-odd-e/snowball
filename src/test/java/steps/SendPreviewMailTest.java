package steps;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import cucumber.api.DataTable;
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

import java.time.LocalDateTime;

public class SendPreviewMailTest {
    public static final String A_COURSE = "A course";
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/";
    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

   /* private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String adminEmail, studentEmail;*/
    private void addCoursesAtTokyo(int offsetDate) throws Throwable {
       LocalDateTime d = LocalDateTime.now();
       d.plusMonths(offsetDate);
       EventTests eventTests = new EventTests();
       eventTests.addCourse(A_COURSE, "Tokyo", d.toLocalDate().toString());
    }

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

    @Given("^There is a contact \"([^\"]*)\" at Tokyo$")
    public void there_is_a_contact_at_Tokyo(String arg1) throws Throwable {
        ContactPerson person = new ContactPerson("hoge", arg1, "fuga");
        person.setLocation("Tokyo");
        person.save();
    }

    @Given("^there are two courses at Tokyo$")
    public void there_are_two_courses_at_Tokyo() throws Throwable {
        addCoursesAtTokyo(1);
        addCoursesAtTokyo(30);
    }

    @When("^I trigger the sending twice$")
    public void i_trigger_the_sending_twice() throws Throwable {
        i_trigger_the_sending_once();
        driver.clickButton("send_button");
    }

    @Given("^add contact \"([^\"]*)\" at Tokyo$")
    public void add_contact_at_Tokyo(String arg1) throws Throwable {
        there_is_a_contact_at_Tokyo(arg1);
    }

    @When("^I trigger the sending once$")
    public void i_trigger_the_sending_once() throws Throwable {
        driver.visit(BASE_URL);
        driver.clickButton("send_button");
    }

    @Then("^\"([^\"]*)\" shouldn't receive$")
    public void shouldn_t_receive(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^there is a course at Tokyo$")
    public void there_is_a_course_at_Tokyo() throws Throwable {
        addCoursesAtTokyo(1);
    }

    @When("^Report page Includes$")
    public void report_page_Includes(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
        throw new PendingException();
    }

}
