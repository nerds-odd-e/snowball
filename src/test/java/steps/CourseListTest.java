package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

/**
 * Created by csd on 31/5/17.
 */
public class CourseListTest {

    private static final String BASE_URL = "http://localhost:8070/massive_mailer/coursedlist.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();

    @When("^user requests courseList$")
    public void user_requests_courseList() throws Throwable {
        driver.visit(BASE_URL);
    }

    @Then("^list all courses$")
    public void list_all_courses() throws Throwable {

    }

}
