package steps;

import com.odde.massivemailer.model.SentMail;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.javalite.activejdbc.Model;
import org.junit.Assert;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class SendPreviewMailTest {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    public static final String A_COURSE = "A course";

    @Given("^there is a course starting from \"([^\"]*)\"$")
    public void there_is_a_course_starting_from(String date) throws Throwable {
        EventTests eventTests = new EventTests();
        eventTests.addCourseWithCountryAndCity(A_COURSE, "Singapore","Singapore", date);
    }

    @Given("^there is a student with email \"([^\"]*)\" loaded for this course$")
    public void there_are_students_with_email_loaded_for_this_course(String email) throws Throwable {
        site.enrollParticipantPage().addStudentTo(A_COURSE, email);
    }

    @When("^i send preview email for this course$")
    public void i_send_preview_email_for_this_course() throws Throwable {
        site.courseListPage().sendPreviewEmailFor(A_COURSE);
    }

    @Then("^\"([^\"]*)\" should receive the pre-course email$")
    public void should_receive_the_pre_course_email(String email) throws Throwable {
        String all = allEmailsThatGotMessages();
        if (!all.contains(email)) {
            BiConsumer<StringBuilder, Model> a = (result, m)-> result.append(m.get("receivers").toString());
            Assert.fail(String.format("This email should have received message: %s\n\tFound message to:\n%s", email, all));
        }
    }

    private String allEmailsThatGotMessages() {
        return SentMail.findAll().stream().map(m ->
                String.format("\t\t%s: %s", m.get("receivers").toString(), m.get("subject").toString())
        ).collect(Collectors.joining("\n"));
    }

    @When("^i send precourse email for this course$")
    public void i_send_precourse_email_for_this_course() throws Throwable {
        site.courseListPage().sendPrecourseEmailFor(A_COURSE);
        driver.pageShouldContain("Successfully");
    }

    @Given("^There is a contact \"([^\"]*)\" at Tokyo$")
    public void there_is_a_contact_at_Tokyo(String email) throws Throwable {
        ContactSteps contactTests = new ContactSteps();
        contactTests.addAContact(email, "Japan", "Tokyo");
    }

    @Then("^\"([^\"]*)\" shouldn't receive any email$")
    public void shouldn_t_receive_email(String email) throws Throwable {
        String all = allEmailsThatGotMessages();
        if (all.contains(email)) {
            Assert.fail(String.format("This email should not receive any message: %s\n\tFound message to:\n%s", email, all));
        }
    }

}
