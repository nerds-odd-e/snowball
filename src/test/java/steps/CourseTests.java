package steps;

import com.odde.massivemailer.model.Course;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.HashMap;
import java.util.Map;

import static com.odde.massivemailer.model.base.Repository.repo;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class CourseTests {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String add_course_url = site.baseUrl() + "add_course.jsp";

    @Given("^There is a course with below details$")
    public void thereIsACourseWithBelowDetails(DataTable dtCourseDetails) {
        visitCreateCoursePage();
        addCourseWithGivenDetails(dtCourseDetails);
        clickSaveCourse();
    }

    private void visitCreateCoursePage() {
        driver.visit(add_course_url);
    }


    private void addCourseWithGivenDetails(DataTable dtCourseDetails) {
        Map<String, Object> vals = dtCourseDetails.asMap(String.class, Object.class);
            fill_in_course_data(vals);

    }

    private void fill_in_course_data(Map<String, Object> course_data) {
        driver.setTextField("courseName", (String)course_data.get("courseName"));
        driver.setTextField("duration", (String)course_data.get("duration"));
        driver.setDropdownValue("country", (String)course_data.get("country"));
        driver.setTextField("city", (String)course_data.get("city"));
        driver.setTextField("startDate", (String)course_data.get("startDate"));
        driver.setTextField("address", (String)course_data.get("address"));
        driver.setTextField("courseDetails", (String)course_data.get("courseDetails"));
        driver.setTextField("instructor", (String)course_data.get("instructor"));
    }

    @When("^Add a course with location \"([^\"]*)\", \"([^\"]*)\"$")
    public void add_a_course_with_location(String city, String country) {
        visitCreateCoursePage();
        addCourseWithLocationAndCourseName(city, country, "CSD");

    }

    private void addCourseWithLocationAndCourseName(String city, String country, String courseName) {
        HashMap<String, Object> vals = createCourseData(city, country, courseName);
        fill_in_course_data(vals);
        clickSaveCourse();
    }

    @Given("^There are (\\d+) courses$")
    public void there_are_courses(int num) {
        for (int i = 1; i <= num; i++){
            repo(Course.class).fromMap(createCourseData("Tokyo", "Japan", "CSD-" + i)).saveIt();
        }
    }

    private HashMap<String, Object> createCourseData(String city, String country, String courseName) {
        HashMap<String, Object> vals = new HashMap<>();
        vals.put("country", country);
        vals.put("city", city);
        vals.put("courseName", courseName);
        vals.put("duration", "3");
        vals.put("startDate", "2017-11-23");
        vals.put("address", "odd-e");
        vals.put("courseDetails", "odd-addresse");
        vals.put("instructor", "someone");
        return vals;
    }

    private void clickSaveCourse() {
        driver.clickButton("save_button");
    }

    @Then("^Course should save and successfully saved message should appear$")
    public void courseListPageShouldContain() {
        driver.expectRedirect(add_course_url);
        assertTrue(driver.getCurrentUrl().contains("status=success&msg=Add%20course%20successfully"));

    }

    @Then("^Course should not save and show error messagea$")
    public void courseShowErrorMassage() {
        driver.expectRedirect(add_course_url);
        assertThat(driver.getCurrentUrl(), containsString("status=fail&msg={%20city:%22cannot%20be%20located%22%20}"));
    }

}
