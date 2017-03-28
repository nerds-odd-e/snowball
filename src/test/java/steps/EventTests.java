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

    @When("^Add an event (\\d+) at (\\d+)$")
    public void clickRegisterEvent(String eventTitle,String location) throws Throwable {
        driver.text_field("evtTitle", eventTitle);
        driver.click_button("register_button");
    }

    @Then("^Event list page should contain (\\d+) at (\\d+)$")
    public void eventListPageShouldContain(String eventTitle,String location) throws Throwable {
        driver.expectRedirect(EVENTLIST_BASE_URL);
        driver.pageShouldContain(eventTitle);
    }
}
