package cucumber.steps;

import com.odde.snowball.model.ContactPerson;
import com.odde.snowball.model.Course;
import com.odde.snowball.model.SentMail;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import static com.odde.snowball.model.base.Repository.repo;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.springframework.util.StringUtils.countOccurrencesOf;

public class UpcomingCourseNotificationSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @When("^We create (\\d+) contacts at (.*?), (.*?)$")
    public void createContactsForLocations(int numberOfContacts, String city, String country) {
        for (int i = 0; i < numberOfContacts; i++) {
            site.addContactPage().addContactWithLocationString("test@test" + i + city+".com", country + "/"+city);
        }
    }

    @When("^We create (\\d+) courses at (.*?), (.*?)$")
    public void createEventsForLocations(int numberOfCourses, String city, String country) {
            EventTests eventTests = new EventTests();
            for (int i = 0; i < numberOfCourses; i++) {
                eventTests.addCourseWithCountryAndCity("Event " + i + city, country, city, "2017-05-17");
            }
    }

    @When("^I send the upcoming courses emails$")
    public void sendTheUpcomingCourseEmails() {
        site.visit("course_list.jsp");
        driver.click("#send_button");
    }

    @Given("^there are (\\d+)/(\\d+) courses and contacts in (.*?), (.*?)$")
    public void there_are_in_Singapore_Singapore(int courses, int contacts, String city, String country) {
        for (int i = 0; i < contacts; i++) {
            repo(ContactPerson.class).fromKeyValuePairs(
                        "city", city,
                        "country", country,
                        "email", "test@test" + i + "-"+city+".com").save();
        }

        for (int i = 0; i < courses; i++) {
            Course course  = repo(Course.class).fromKeyValuePairs("courseName", "Event " + i + " in " + city, "courseDetails", "Event " + i + " in " + city, "country", country, "city", city);
            assertNotNull(course.save().getId());
        }
    }

    @Then("^there should be in total (\\d+) courses in all the emails$")
    public void there_are_in_total_in_all_the_emails(int courses) {
        int sum = repo(SentMail.class).findAll().stream().map(mail-> countOccurrencesOf(mail.getContent(), "Event")).mapToInt(Integer::intValue).sum();
        assertEquals(courses, sum);
    }
}

