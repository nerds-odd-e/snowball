package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

public class EventTests {

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/add_event.jsp";
    private String EVENTLIST_BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    @Given("^I am on Add Event page$")
    public void visitAddEventPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^Create course \"([^\"]*)\" in \"([^\"]*)\"$")
    public void addEventAndSelectLocationFromDropdown(String courseName,String location) throws Throwable {
        driver.setTextField("coursename", courseName);
        driver.setTextField("coursedetails", "nothing important");
        driver.setTextField("startdate", "2017-07-21");
        driver.setDropdownValue("location", location);
    }

    @When("^I click the save button$")
    public void clickRegisterEvent() throws Throwable {
        driver.clickButton("save_button");
    }

    @Then("^Course save page should display \"([^\"]*)\"$")
    public void eventListPageShouldContain(String message) throws Throwable {
        driver.pageShouldContain(message);
    }
}
