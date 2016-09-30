package gradle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.driver.WebDriverFactory;
import gradle.cucumber.driver.UiElement;
import gradle.cucumber.driver.WebDriverWrapper;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameStepdefs {

    private WebDriverWrapper spectatorViewDriver = WebDriverFactory.getDefaultDriver();
    private WebDriverWrapper playerViewDriver;

    private String BASE_URL = "http://localhost:8070/massive_mailer/";

    @Given("^I am at Emerson's landing page$")
    public void iAmAtEmersonsLandingPage() throws Throwable {
        spectatorViewDriver.visit(BASE_URL + "game_create.jsp");
        spectatorViewDriver.findElementById("distance");
        spectatorViewDriver.findElementById("btnCreate");
    }

    @When("^I submit a distance of (\\d+)$")
    public void submitValidDistance(int dist) throws Throwable {
        spectatorViewDriver.text_field("distance", Integer.toString(dist));
        spectatorViewDriver.click_button("btnCreate");
    }

    @And("^Distance should be (\\d+)$")
    public void checkDistance(int dist) throws Throwable {
      assertEquals(Integer.toString(dist), spectatorViewDriver.findElementById("inputDistance").getAttribute("value"));
    }

    @When("^Open QR Code Scanner$")
    public void openQRCodeScanner() throws Throwable {
        spectatorViewDriver.visit("https://zxing.org/w/decode.jspx");

    }

    @When("^Scanner checks \"(.*)\"")
    public void scannerCheckUrl(String url) throws Throwable {
        UiElement text_field = spectatorViewDriver.findElementByName("u");
        text_field.sendKeys(url);
        spectatorViewDriver.clickXPath("//*[@id='upload']/tbody/tr[1]/td[3]/input");
    }

    @Then("^Scanner should return \"([^\"]*)\"$")
    public void qrCodeShouldReturn(String expected) throws Throwable {
        spectatorViewDriver.pageShouldContain(expected);
    }

    @Given("^a player joins the game$")
    public void aPlayerJoinsTheGame() throws Throwable {
        new MyStepdefs().loginWithEmail("terry2@odd-e.com");
    }

    @When("^a game of distance (\\d+) is created$")
    public void createGameWithDistX(int dist) throws Throwable {
        iAmAtEmersonsLandingPage();
        submitValidDistance(dist);
    }

    @Given("^a player joins the game on a separate window$")
    public void aPlayerJoinsTheGameOnASeparateWindow() throws Throwable {
        playerViewDriver = WebDriverFactory.getAdditionalDriver();
        playerViewDriver.visit(BASE_URL + "game_login.jsp");
        playerViewDriver.text_field("email", "terry@odd-e.com");
        playerViewDriver.click_button("add_button");
    }

    @Then("^the player UI should display \"([^\"]*)\" of (\\d+)$")
    public void playerUIShouldDisplay(String elementId, int value){
        playerViewDriver.expectElementWithIdToContainValue(elementId, value);
    }

    @Then("^the spectator UI \"([^\"]*)\" should display that (\\d+) player joined within (\\d+) seconds")
    public void spectatorUIShouldDisplayXinYSeconds(String elementId, int value, int seconds){
        spectatorViewDriver.expectElementWithIdToContainTextInXSeconds(elementId, Integer.toString(value), seconds);
    }


    @Then("^The spectator should see two cars on the screen$")
    public void theSpectatorShouldSeeTwoCarsOnTheScreen() throws Throwable {
        spectatorViewDriver.visit(new MyStepdefs().BASE_URL + "game_create.jsp");
        spectatorViewDriver.text_field("distance", "5");
        spectatorViewDriver.click_button("btnCreate");
        TimeUnit.SECONDS.sleep(4);
        assertEquals(2, spectatorViewDriver.countElementWithClass("racer"));
    }

    @When("^the player roll the die$")
    public void thePlayerRollTheDie() throws Throwable {
        playerViewDriver.click_button("normalRollButton");
    }

    @Then("^the player's move should be displayed on the spectator view$")
    public void thePlayerSMoveShouldBeDisplayedOnTheSpectatorView() throws Throwable {
        assertEquals(1, spectatorViewDriver.countElementWithClass("car"));
        int carPos = Integer.parseInt(spectatorViewDriver.findElementById("carPos").getText());
        assertEquals(carPos * 20, spectatorViewDriver.getElementMarginWithClass("car"));
    }

    @When("^another player with the same email joins the game$")
    public void anotherPlayerWithTheSameEmailJoinsTheGame() throws Throwable {
        new MyStepdefs().loginWithEmail("terry@odd-e.com");
    }

    @When("^another player joins the game on a separate window$")
    public void anotherPlayerJoinsTheGameOnASeparateWindow() throws Throwable {
        aPlayerJoinsTheGameOnASeparateWindow();
    }
}
