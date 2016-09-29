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
import static org.junit.Assert.assertTrue;

public class GameStepdefs {

    private WebDriverWrapper driver = SingleDrive.getDriver();
    private WebDriverWrapper separateDriver;


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
        new MyStepdefs().loginWithEmail("terry@odd-e.com");
    }

    @Given("^a game is started$")
    public void aGameIsStarted() throws Throwable {
        iAmAtEmersonsLandingPage();
        submitValidDistance(30);
    }

    @When("^another player joins the game$")
    public void anotherPlayerJoinsTheGame() throws Throwable {
        new MyStepdefs().loginWithEmail("another_terry@odd-e.com");
    }

    @Then("^The spectator should see two cars on the screen$")
    public void theSpectatorShouldSeeTwoCarsOnTheScreen() throws Throwable {
        driver.visit(new MyStepdefs().BASE_URL + "gameSpectator");
        assertEquals(2, driver.countElementWithClass("car"));
    }

    @Given("^a player joins the game on a separate window$")
    public void aPlayerJoinsTheGameOnASeparateWindow() throws Throwable {
        separateDriver = new WebDriverWrapper();
        separateDriver.visit(BASE_URL + "game_login.jsp");
        separateDriver.text_field("email", "terry@odd-e.com");
        separateDriver.click_button("add_button");
    }

    @When("^the player roll the die$")
    public void thePlayerRollTheDie() throws Throwable {
        separateDriver.click_button("normalRollButton");
        separateDriver.closeAll();
    }

    @Then("^the player's move should be displayed on the spectator view$")
    public void thePlayerSMoveShouldBeDisplayedOnTheSpectatorView() throws Throwable {
        assertEquals(1, driver.countElementWithClass("car"));
        int carPos = Integer.parseInt(driver.findElementById("carPos").getText());
        assertEquals(carPos * 20, driver.getElementMarginWithClass("car"));
    }
}
