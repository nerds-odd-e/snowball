package steps;

import com.odde.massivemailer.model.Course;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourseDetailSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String courseDetailUrl = site.baseUrl() + "course_detail.jsp";

    @When("^I visit \"([^\"]*)\" detail page from course list page$")
    public void iVisitDetailPage(String courseName) {
        site.visit("course_list.jsp");
        Course course = Course.getCourseByName(courseName);
        driver.visit(courseDetailUrl + "?id=" + course.getStringId());
        driver.pageShouldContain("Course Detail");
        driver.pageShouldContain(courseName);
    }

    @When("^I enroll participants to \"([^\"]*)\" from course detail page$")
    public void iEnrollParticipantsToFromCourseDetailPage(String courseName, DataTable participantsData) {
        iVisitDetailPage(courseName);
        String participants = participantsData.asList(String.class).stream().collect(Collectors.joining("\\n"));
        site.enrollParticipantPage().enrollParticipants(courseName, participants);
    }

    @Then("^participant with correct information appears on \"([^\"]*)\" course detail page$")
    public void participantWithCorrectInformationAppearsOnCourseDetailPage(String courseName, DataTable participants) {
        String tableContent = driver.findElementById("courseTable").getText();
        participants.asLists(String.class).forEach(participant -> {
            assertTrue(tableContent.contains(participant.get(0)));
            assertTrue(tableContent.contains(participant.get(1)));
        });
    }

    @And("^participant with invalid information appears in the enroll form$")
    public void carryAppearsInTheEnrollForm(DataTable participantsData) {
        String errorParticipantData = participantsData.asList(String.class).get(0);
        Optional<String> error = driver.executeJavaScript("return document.getElementById('participants').value");
        assertTrue(error.isPresent());
        assertEquals(errorParticipantData, error.get());
        driver.pageShouldContain("Check the following participants.");
    }

    @Then("^\"([^\"]*)\" course detail page is shown$")
    public void courseDetailPageIsShown(String courseName) {
        driver.pageShouldContain(courseName);
    }
}
