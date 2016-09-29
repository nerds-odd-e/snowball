package gradle.cucumber;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.SingleDrive;
import gradle.cucumber.driver.UiElement;
import gradle.cucumber.driver.WebDriverWrapper;

import static org.junit.Assert.assertEquals;

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
    public void checkDistance(int dist) throws Throwable {
      assertEquals(Integer.toString(dist), driver.findElementById("inputDistance").getAttribute("value"));
    }

    @When("^Open QR Code Scanner$")
    public void openQRCodeScanner() throws Throwable {
        driver.visit("https://zxing.org/w/decode.jspx");

    }

    @When("^Scanner checks \"(.*)\"")
    public void scannerCheckUrl(String url) throws Throwable {
        UiElement text_field = driver.findElementByName("u");
        text_field.sendKeys(url);
        driver.clickXPath("//*[@id='upload']/tbody/tr[1]/td[3]/input");
    }

    @Then("^Scanner should return \"([^\"]*)\"$")
    public void qrCodeShouldReturn(String expected) throws Throwable {
        driver.pageShouldContain(expected);
    }

    @Given("^a player joins the game$")
    public void aPlayerJoinsTheGame() throws Throwable {

        throw new PendingException();
    }

    @Given("^a game is started$")
    public void aGameIsStarted() throws Throwable {
        // driver needs to go to emersonsgame
        // driver needs to input distance
        // driver at spectator page, it's ok now
    }
}
