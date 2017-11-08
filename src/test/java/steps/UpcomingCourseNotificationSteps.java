package steps;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.SentMail;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.flywaydb.core.internal.util.StringUtils;

import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import static org.junit.Assert.assertEquals;

public class UpcomingCourseNotificationSteps {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/course_list.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

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

    @When("^I send the upcoming courses emails$")
    public void sendTheUpcomingCourseEmails() throws Throwable {
        driver.visit(BASE_URL);
        driver.clickButton("send_button");
    }

    @Given("^there are (\\d+)/(\\d+) courses and contacts in (.*?), (.*?)$")
    public void there_are_in_Singapore_Singapore(int courses, int contacts, String city, String country) throws Throwable {
        ContactSteps contactTests = new ContactSteps();
        for (int i = 0; i < contacts; i++) {
            ContactPerson.createContact(city, country, "test@test" + i + "_"+city+".com");
        }

        EventTests eventTests = new EventTests();
        for (int i = 0; i < courses; i++) {
            Course.createIt("coursename", "Event " + i +" in " + city, "location", country + "/" + city, "startdate", "2017-05-17");
        }
    }

    @Then("^there should be in total (\\d+) courses in all the emails$")
    public void there_are_in_total_in_all_the_emails(int courses) throws Throwable {
        int sum = SentMail.findAll().stream().map(mail-> StringUtils.countOccurrencesOf(((SentMail)mail).getContent(), "Event")).mapToInt(Integer::intValue).sum();
        assertEquals(courses, sum);
    }
}

