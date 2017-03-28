package steps;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Event;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverFactory;
import steps.driver.WebDriverWrapper;

import java.util.List;

public class SendAllEventsTest {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private int numberOfEvents, numberOfEventsInLocation;
    private int  numberOfContacts, numberOfContactsInLocation;

    @Given("^visit event list page$")
    public void VisitEventListPage() throws Throwable {
        driver.visit(BASE_URL);
    }


    @When("^(\\d+) out of (\\d+) contacts are in Singapore$")
    public void numberOfContactIs(int numberOfContactsInLocation, int numOfContacts) throws Throwable {
        this.numberOfContactsInLocation = numberOfContactsInLocation;
        this.numberOfContacts = numOfContacts;
        MyStepdefs contactTests = new MyStepdefs();
        for (int i=0;i<this.numberOfContacts;i++) {
            contactTests.addAContact("test@test"+i+".com","Singapore");
        }
    }

    @When("^(\\d+) out of (\\d+) events are in Singapore$")
    public void numberOfEventIs(int numberOfEventsInLocation, int numberOfEvents) throws Throwable {
        this.numberOfEventsInLocation = numberOfEventsInLocation;
        this.numberOfEvents = numberOfEvents;
        EventTests eventTests = new EventTests();
        for(int i=0;i<this.numberOfEventsInLocation;i++) {
            eventTests.visitAddEventPage();
            eventTests.clickRegisterEvent("Event "+i,"Singapore");
        }
        for(int i=0;i<this.numberOfEvents-this.numberOfEventsInLocation;i++) {
            eventTests.visitAddEventPage();
            eventTests.clickRegisterEvent("Event "+i,"Not-Singapore");
        }
    }

    @When("^I click send button$")
    public void ClickSendButton() throws Throwable {
        driver.visit(BASE_URL);
        driver.click_button("send_button");
    }

    @Then("^([^\"]*) contact\\(s\\) receive an email that contains ([^\"]*)$")
    public void contactReceiveEmailContainsEvents(String numberOfEmailRecipients, String numberOfEventsInEmail) throws Throwable {
        String expectedMessage = String.format("%s emails contain %s events sent.", numberOfEmailRecipients, numberOfEventsInEmail);
        driver.expectElementWithIdToContainText("message", expectedMessage);
    }
}
