package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> vals = dtCourseDetails.asMap(String.class, String.class);
            fill_in_course_data(vals);

    }

    private void fill_in_course_data(Map<String, String> course_data) {
        driver.setTextField("coursename", course_data.get("coursename"));
        driver.setTextField("duration", course_data.get("duration"));
        driver.setDropdownValue("country", course_data.get("country"));
        driver.setTextField("city", course_data.get("city"));
        driver.setTextField("startdate", course_data.get("startdate"));
        driver.setTextField("address", course_data.get("address"));
        driver.setTextField("coursedetails", course_data.get("coursedetails"));
        driver.setTextField("instructor", course_data.get("instructor"));
    }

    @When("^Add a course with location \"([^\"]*)\", \"([^\"]*)\"$")
    public void add_a_course_with_location(String city, String country) throws Throwable {
        addCourseWithLocationAndCourseName(city, country, "CSD");

    }

    public void addCourseWithLocationAndCourseName(String city, String country, String courseName) throws Throwable {
        HashMap<String, String> vals = createCourseData(city, country, courseName);
        fill_in_course_data(vals);
        clickSaveCourse();
    }

    @Given("^There are (\\d+) courses$")
    public void there_are_courses(int num) throws Throwable {
        String country = "Japan";
        String city = "Tokyo";
        for (int i = 1; i <= num; i++){
            visitCreateCoursePage();
            addCourseWithLocationAndCourseName(city, country,"CSD-" + i);
        }
    }

    private HashMap<String, String> createCourseData(String city, String country, String coursename) {
        HashMap<String, String> vals = new HashMap<>();
        vals.put("country", country);
        vals.put("city", city);
        vals.put("coursename", coursename);
        vals.put("duration", "3");
        vals.put("startdate", "2017-11-23");
        vals.put("address", "odd-e");
        vals.put("coursedetails", "odd-addresse");
        vals.put("instructor", "someone");
        return vals;
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
