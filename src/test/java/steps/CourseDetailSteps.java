package steps;

import com.odde.massivemailer.model.Course;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.stream.Collectors;

public class CourseDetailSteps {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String courseDetailUrl = site.baseUrl() + "course_detail.jsp";

    @When("^I visit \"([^\"]*)\" detail page from course list page$")
    public void iVisitDetailPage(String courseName) {
        site.visit("course_list.jsp");
        Course course = Course.getCourseByName(courseName);
        driver.clickById("course_detail_link_"+course.getId().toString());
        driver.pageShouldContain("Course Detail");
        driver.pageShouldContain(courseName);
    }

    @When("^I enroll participants to \"([^\"]*)\" from course detail page$")
    public void iEnrollParticipantsToFromCourseDetailPage(String courseName, DataTable participantsData) throws Throwable {
        String participants = participantsData.asList(String.class).stream().collect(Collectors.joining("\n"));
        Course course = Course.getCourseByName(courseName);
        driver.visit(courseDetailUrl + "?id=" + course.getId().toString());
        driver.setTextField("participants", participants);
        driver.clickButton("add_button");
    }

    @Then("^participant with correct information appears on \"([^\"]*)\" course detail page$")
    public void participantWithCorrectInformationAppearsOnCourseDetailPage(String courseName, DataTable participantsData) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Carry appears in the enroll form$")
    public void carryAppearsInTheEnrollForm(DataTable participantsData) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\" course detail page is shown$")
    public void courseDetailPageIsShown(String courseName) {
        driver.pageShouldContain(courseName);
    }
}
