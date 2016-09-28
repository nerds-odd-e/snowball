package gradle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.SingleDrive;
import gradle.cucumber.driver.WebDriverWrapper;

public class GameStepdefs {

    private WebDriverWrapper driver = SingleDrive.getDriver();
    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @Given("^I am at Emerson's landing page$")
    public void iAmAtEmersonsLandingPage() throws Throwable {
        driver.visit(BASE_URL + "game_create.jsp");
        driver.findElementById("distance");
        driver.findElementById("btnCreate");
    }

    @When("^I submit a distance of (\\d+)$")
    public void submitValidDistance(int dist) throws Throwable {
        driver.text_field("distance", Integer.toString(dist));
        driver.click_button("btnCreate");
    }

    @And("^Distance should be (\\d+)$")
    public void checkDistance(int dist) throws Throwable{
      //  driver.expectElementWithIdToContainText("inputDistance", ""+dist);
        driver.findElementById("inputDistance");
    }

}
