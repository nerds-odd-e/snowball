package steps;

import com.odde.massivemailer.model.Course;
import com.odde.massivemailer.model.Participant;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CourseDetailSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String courseDetailUrl = site.baseUrl() + "course_detail.jsp";

    @When("^I visit \"([^\"]*)\" detail page from course list page$")
    public void iVisitDetailPage(String courseName) {
        site.visit("course_list.jsp");
        Course course = Course.getCourseByName(courseName);
        driver.clickById("course_detail_link_" + course.getId().toString());
        driver.pageShouldContain("Course Detail");
        driver.pageShouldContain(courseName);
    }

    @When("^I enroll participants to \"([^\"]*)\" from course detail page$")
    public void iEnrollParticipantsToFromCourseDetailPage(String courseName, DataTable participantsData) throws Throwable {
        String participants = participantsData.asList(String.class).stream().collect(Collectors.joining("\\n"));
        Course course = Course.getCourseByName(courseName);
        driver.visit(courseDetailUrl + "?id=" + course.getId().toString());
        driver.executeJavaScript("document.getElementById('participants').value = '" + participants + "';");
        driver.clickButton("add_button");
    }

    @Then("^participant with correct information appears on \"([^\"]*)\" course detail page$")
    public void participantWithCorrectInformationAppearsOnCourseDetailPage(String courseName, DataTable participants) throws Throwable {
        String tableContent = driver.findElementById("courseTable").getText();
        participants.asLists(String.class).forEach(participant -> {
            assertTrue(tableContent.contains(participant.get(0)));
            assertTrue(tableContent.contains(participant.get(1)));
        });
    }

    @And("^participant with invalid information appears in the enroll form$")
    public void carryAppearsInTheEnrollForm(DataTable participantsData) throws Throwable {
        String errorParticipantData = participantsData.asList(String.class).get(0);
        driver.expectElementWithIdToContainValue("participants", errorParticipantData);
        driver.pageShouldContain("Fail");
    }

    @Then("^\"([^\"]*)\" course detail page is shown$")
    public void courseDetailPageIsShown(String courseName) {
        driver.pageShouldContain(courseName);
    }
}
