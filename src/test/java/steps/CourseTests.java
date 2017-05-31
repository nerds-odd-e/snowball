package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import java.util.Date;

public class CourseTests {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/add_event.jsp";

    @Given("^I am on create new course page$")
    public void visitAddEventPage() throws Throwable {
        driver.visit(BASE_URL);
    }
    @When("^Add a course with below details$")
    public void addCourseWithGivenDetails(String coursename,String duration,String location,Date startdate,String address,String coursedetails,String instructor) throws Throwable {
        driver.setTextField("coursename", coursename);
        driver.setTextField("duration", duration);
        driver.setTextField("startdate", startdate.toString());
        driver.setTextField("address", address);
        driver.setTextField("coursedetails", coursedetails);
        driver.setTextField("instructor", instructor);
        driver.setDropdownValue("location", location);
    }


    @When("^I click the Create button$")
    public void clickRegisterEvent() throws Throwable {
        driver.clickButton("save_button");
    }

    @Then("^Course should save and successfully saved message should appear$")
    public void eventListPageShouldContain(String eventTitle,String eventLocation) throws Throwable {
        driver.expectRedirect(BASE_URL);
    }
}
