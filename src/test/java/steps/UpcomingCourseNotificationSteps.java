package steps;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.SentMail;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.flywaydb.core.internal.util.StringUtils;

import org.javalite.activejdbc.Model;
import org.joda.time.DateTime;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import static junit.framework.TestCase.assertTrue;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class UpcomingCourseNotificationSteps {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/course_list.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private long currentTotalEmailCounts = 0L;
    private int noOfDaysBefore = 0;
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
        for (int i = 0; i < contacts; i++) {
            assertTrue(ContactPerson.createContact(city, country, "test@test" + i + "_"+city+".com"));
        }

        for (int i = 0; i < courses; i++) {
            Course course  = new Course("Event " + i + " in " + city,"Event " + i + " in " + city,country + "/" + city);
            assertTrue(course.saveIt());
        }
    }

    @Then("^there should be in total (\\d+) courses in all the emails$")
    public void there_are_in_total_in_all_the_emails(int courses) throws Throwable {
        int sum = SentMail.findAll().stream().map(mail-> StringUtils.countOccurrencesOf(((SentMail)mail).getContent(), "Event")).mapToInt(Integer::intValue).sum();
        assertEquals(courses, sum);
    }

    @Given("^there is a contact \"contact@gmail.com\" at Singapore, Singapore created (.*?) before")
    public void there_is_a_contact_at_Singapore_Singapore(String noOfDaysBeforeString) throws Throwable {
        noOfDaysBefore = Integer.valueOf(noOfDaysBeforeString.split(" ")[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -noOfDaysBefore);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        assertTrue(ContactPerson.createContactAndUpdateSentDate("Singapore", "Singapore", "contact@gmail.com", format.format(calendar.getTime())));
}

    @And("^there is a upcoming course at Singapore, Singapore")
    public void there_is_upcoming_course_in_Singapore() throws Throwable {
        Course.createIt("coursename", "Event " + "1" +" in " + "Singapore", "location", "Singapore" + "/" + "Singapore", "startdate", "2017-05-17");
    }

    @And("^I have sent the upcoming courses emails")
    public void i_have_sent_the_upcoming_courses_emails() throws Throwable {
        driver.visit(BASE_URL);
        driver.clickButton("send_button");
        ContactPerson contact = ContactPerson.getContactByEmail("contact@gmail.com");
        assertNotNull(contact);
        assertEquals(formatDate(-noOfDaysBefore), contact.getDateSent().split(" ")[0]);
    }

    @When("^a new contact \"new_contact@gmail.com\" is added at Singapore, Singapore$")
    public void new_contact_at_singapore() throws Throwable {
        ContactPerson.createContact("Singapore", "Singapore", "new_contact@gmail.com");
    }

    @When("^a new contact \"new_contact@gmail.com\" is not added at Singapore, Singapore$")
    public void new_contact_at_singaporec() throws Throwable {
        // do nothing
    }

    @And("^I send the upcoming courses emails again now")
    public void send_the_upcoming_courses_emails_again() throws Throwable {
        driver.visit(BASE_URL);
        driver.clickButton("send_button");
    }

    @And("^I have marked the current emails sent")
    public void ivemarkedthecurrentemailssent() {
        this.currentTotalEmailCounts = SentMail.count(); // original no of email
    }

    private String formatDate(int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, amount);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(calendar.getTime());
    }

    @Then("^in total, there should be (.*?)$")
    public void there_should_be_upcoming_course_email_resending(String noOfNewEmailsSentString) throws Throwable {
        int noOfNewEmailsSent = Integer.valueOf(noOfNewEmailsSentString.split(" ")[0]);
        int totalNewEmails = (int)(SentMail.count() - this.currentTotalEmailCounts);
        assertThat(totalNewEmails, is(noOfNewEmailsSent));
    }
}

