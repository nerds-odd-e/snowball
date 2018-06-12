package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CourseTests {

    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();
    private String add_course_url = site.baseUrl() + "add_course.jsp";

    @Given("^I am on create new course page$")
    public void visitCreateCoursePage() throws Throwable {
        driver.visit(add_course_url);
    }


    @When("^Add a course with below details$")
    public void addCourseWithGivenDetails(DataTable dtCourseDetails) throws Throwable {
        List<List<String>> courses = dtCourseDetails.raw();
        courses = courses.subList(1, courses.size());
        for(List<String> val:courses){
            driver.setTextField("coursename", val.get(0));
            driver.setTextField("duration", val.get(1));
            driver.setDropdownValue("country", val.get(2));
            driver.setTextField("city", val.get(3));
            driver.setTextField("startdate", val.get(4));
            driver.setTextField("address", val.get(5));
            driver.setTextField("coursedetails", val.get(6));
            driver.setTextField("instructor", val.get(7));
        }
    }


    @When("^I click the Create button$")
    public void clickSaveCourse() throws Throwable {
        driver.clickButton("save_button");
    }

    @Then("^Course should save and successfully saved message should appear$")
    public void courseListPageShouldContain() throws Throwable {
        driver.expectRedirect(add_course_url);
        assertTrue(driver.getCurrentUrl().contains("status=success&msg=Add%20course%20successfully"));

    }

    @Then("^Course should not save and show error messagea$")
    public void courseShowErrorMassage() throws Throwable {
        driver.expectRedirect(add_course_url);
        System.out.println(driver.getCurrentUrl());
        assertTrue(driver.getCurrentUrl().contains("status=failed&msg=CityName%20is%20invalid"));
    }
}
