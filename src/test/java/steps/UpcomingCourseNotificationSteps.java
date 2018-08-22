package steps;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.SentMail;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.flywaydb.core.internal.util.StringUtils;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class UpcomingCourseNotificationSteps {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @When("^We create (\\d+) contacts at (.*?), (.*?)$")
    public void createContactsForLocations(int numberOfContacts, String city, String country) throws Throwable {
        ContactSteps contactTests = new ContactSteps();
        for (int i = 0; i < numberOfContacts; i++) {
            contactTests.addAContact("test@test" + i + city+".com", country + "/"+city);
        }
    }

    @When("^We create (\\d+) courses at (.*?), (.*?)$")
    public void createEventsForLocations(int numberOfCourses, String city, String country) throws Throwable {
            EventTests eventTests = new EventTests();
            for (int i = 0; i < numberOfCourses; i++) {
                eventTests.addCourseWithCountryAndCity("Event " + i + city, country, city, "2017-05-17");
            }
    }

    @Given("^the send mail history is empty$")
    public void the_send_mail_history_is_empty() throws Throwable {
        SentMail.deleteAll();
    }

    @When("^I send the upcoming courses emails$")
    public void sendTheUpcomingCourseEmails() throws Throwable {
        site.visit("course_list.jsp");
        driver.clickButton("send_button");
    }

    @Given("^there are (\\d+)/(\\d+) courses and contacts in (.*?), (.*?)$")
    public void there_are_in_Singapore_Singapore(int courses, int contacts, String city, String country) throws Throwable {
        for (int i = 0; i < contacts; i++) {
            assertTrue(ContactPerson.create(
                    "city", city,
                    "country", country,
                    "email", "test@test" + i + "-"+city+".com").saveIt());
        }

        for (int i = 0; i < courses; i++) {
            Course course  = Course.create("coursename", "Event " + i + " in " + city, "coursedetails", "Event " + i + " in " + city, "country", country, "city", city);
            assertTrue(course.saveIt());
        }
    }

    @Then("^there should be in total (\\d+) courses in all the emails$")
    public void there_are_in_total_in_all_the_emails(int courses) throws Throwable {
        int sum = SentMail.findAll().stream().map(mail-> StringUtils.countOccurrencesOf(((SentMail)mail).getContent(), "Event")).mapToInt(Integer::intValue).sum();
        assertEquals(courses, sum);
    }
}

