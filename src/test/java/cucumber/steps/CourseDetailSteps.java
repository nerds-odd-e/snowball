package cucumber.steps;

import com.odde.snowball.model.Course;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourseDetailSteps {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String courseDetailUrl = site.baseUrl() + "course_detail.jsp";

    @When("^I visit \"([^\"]*)\" detail page from course list page$")
    public void iVisitDetailPage(String courseName) {
        site.visit("course_list.jsp");
        Course course = Course.getCourseByName(courseName);
        driver.visit(courseDetailUrl + "?id=" + course.getStringId());
        driver.expectPageToContainText("Course Detail");
        driver.expectPageToContainText(courseName);
    }

    @When("^I enroll participants to \"([^\"]*)\" from course detail page$")
    public void iEnrollParticipantsToFromCourseDetailPage(String courseName, List<String> participantsData) {
        iVisitDetailPage(courseName);
        String participants = participantsData.stream().collect(Collectors.joining("\\n"));
        site.enrollParticipantPage().enrollParticipants(courseName, participants);
    }

    @Then("^participant with correct information appears on \"([^\"]*)\" course detail page$")
    public void participantWithCorrectInformationAppearsOnCourseDetailPage(String courseName, List<List<String>> participants) {
        participants.forEach(participant -> {
            driver.expectElementToContainText("#courseTable", participant.get(0));
            driver.expectElementToContainText("#courseTable", participant.get(1));
        });
    }

    @And("^participant with invalid information appears in the enroll form$")
    public void carryAppearsInTheEnrollForm(List<String> participantsData) {
        String errorParticipantData = participantsData.get(0);
        Optional<String> error = driver.executeJavaScript("return document.getElementById('participants').value");
        assertTrue(error.isPresent());
        assertEquals(errorParticipantData, error.get());
        driver.expectPageToContainText("Check the following participants.");
    }

    @Then("^\"([^\"]*)\" course detail page is shown$")
    public void courseDetailPageIsShown(String courseName) {
        driver.expectPageToContainText(courseName);
    }
}
