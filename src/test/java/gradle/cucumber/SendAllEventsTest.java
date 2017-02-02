package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.WebDriverWrapper;

public class SendAllEventsTest {
    private static final String BASE_URL = "http://localhost:8070/massive_mailer/eventlist.jsp";

    private WebDriverWrapper driver = WebDriverFactory.getDefaultDriver();
    private int numberOfEvents;
    private int  numberOfContacts;

    @Given("^visit event list page$")
    public void VisitEventListPage() throws Throwable {
        driver.visit(BASE_URL);
    }

    @When("^number of contact is \"([^\"]*)\"$")
    public void numberOfContactIs(String numberOfContacts) throws Throwable {
        this.numberOfContacts = Integer.parseInt(numberOfContacts);
        MyStepdefs contactTests = new MyStepdefs();
        for (int i=0;i<this.numberOfContacts;i++) {
            contactTests.addAContact("test@test"+i+".com");
            contactTests.iShouldGetAnAlertDialogWithMessage("Add contact successfully");
        }
    }

    @When("^number of event is \"([^\"]*)\"$")
    public void numberOfEventIs(String numberOfEvents) throws Throwable {
        this.numberOfEvents = Integer.parseInt(numberOfEvents);
        EventTests eventTests = new EventTests();
        for(int i=0;i<this.numberOfEvents;i++) {
            eventTests.visitAddEventPage();
            eventTests.clickRegisterEvent("Event "+i);
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
