package cucumber.steps;

import com.odde.snowball.model.Course;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.HashMap;
import java.util.Map;

import static com.odde.snowball.model.base.Repository.repo;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThat;

public class CourseTests {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String add_course_url = site.baseUrl() + "add_course.jsp";

    @Given("^There is a course with below details$")
    public void thereIsACourseWithBelowDetails(Map<String, String> dtCourseDetails) {
        visitCreateCoursePage();
        fill_in_course_data(dtCourseDetails);

        clickSaveCourse();
    }

    private void visitCreateCoursePage() {
        driver.visit(add_course_url);
    }

    private void fill_in_course_data(Map<String, String> course_data) {
        driver.setTextField("courseName", course_data.get("courseName"));
        driver.setTextField("duration", course_data.get("duration"));
        driver.selectDropdownByValue("country", course_data.get("country"));
        driver.setTextField("city", course_data.get("city"));
        driver.setTextField("startDate", course_data.get("startDate"));
        driver.setTextField("address", course_data.get("address"));
        driver.setTextField("courseDetails", course_data.get("courseDetails"));
        driver.setTextField("instructor", course_data.get("instructor"));
    }

    @When("^Add a course with location \"([^\"]*)\", \"([^\"]*)\"$")
    public void add_a_course_with_location(String city, String country) {
        visitCreateCoursePage();
        addCourseWithLocationAndCourseName(city, country);

    }

    private void addCourseWithLocationAndCourseName(String city, String country) {
        HashMap<String, String> vals = createCourseData(city, country, "CSD");
        fill_in_course_data(vals);
        clickSaveCourse();
    }

    @Given("^There are (\\d+) courses$")
    public void there_are_courses(int num) {
        for (int i = 1; i <= num; i++){
            repo(Course.class).fromMap(createCourseData("Tokyo", "Japan", "CSD-" + i)).save();
        }
    }

    private HashMap<String, String> createCourseData(String city, String country, String courseName) {
        HashMap<String, String> vals = new HashMap<>();
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
        driver.click("#save_button");
    }

    @Then("^Course should save and successfully saved message should appear$")
    public void courseListPageShouldContain() {
        driver.expectRedirect(add_course_url);
        driver.expectURLToContain("status=success&msg=Add%20course%20successfully");

    }

    @Then("^Course should not save and show error messagea$")
    public void courseShowErrorMassage() {
        driver.expectRedirect(add_course_url);
        driver.expectURLToContain("status=fail&msg={%20geoLocation.latitude:%22This%20location%20cannot%20be%20found%22%20}");
    }

}
