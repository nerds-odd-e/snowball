package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourseTests {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/add_event.jsp";

    @Given("^I am on create new course page$")
    public void visitCreateCoursePage() throws Throwable {
        driver.visit(BASE_URL);
    }


    @When("^Add a course with below details$")
    public void addCourseWithGivenDetails(DataTable dtCourseDetails) throws Throwable {
        List<List<String>> courses = dtCourseDetails.raw();
        courses = courses.subList(1, courses.size());
        for(List<String> val:courses){
            driver.setTextField("coursename", val.get(0));
            driver.setTextField("duration", val.get(1));
            driver.setDropdownValue("location", val.get(2));
            //driver.setTextField("city", val.get(3));
            driver.setTextField("startdate", val.get(3));
            driver.setTextField("address", val.get(4));
            driver.setTextField("coursedetails", val.get(5));
            driver.setTextField("instructor", val.get(6));
        }
    }


    @When("^I click the Create button$")
    public void clickSaveEvent() throws Throwable {
        driver.clickButton("save_button");
    }

    @Then("^Course should save and successfully saved message should appear$")
    public void eventListPageShouldContain() throws Throwable {
        driver.expectRedirect(BASE_URL);
        assertTrue(driver.getCurrentUrl().contains("status=success&msg=Add%20course%20successfully"));

    }

    @Then("^Course should not save and show error messagea$")
    public void eventShowErrorMassage() throws Throwable {
        driver.expectRedirect(BASE_URL);
        assertTrue(driver.getCurrentUrl().contains("status=failed&msg=CityName%20is%20invalid"));
    }
}