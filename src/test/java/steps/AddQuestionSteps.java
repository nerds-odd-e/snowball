package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.UiElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class AddQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^Add Questionを開いている$")
    public void AddQuestion() {
        site.visit("onlinetest/add_question.jsp");
        driver.pageShouldContain("Add Question");
    }

    @Given("^Descriptionに\"([^\"]*)\" を入力する$")
    public void descriptionに_が入力されている(String description) throws Throwable {
        driver.setTextField("description", description);
    }

    @Given("^option(\\d+)に\"([^\"]*)\"が入力されている$")
    public void option_に_が入力されている(int optionNumber, String optionName) throws Throwable {
        driver.setTextField("option" + optionNumber, optionName);
    }

    @Given("^\"([^\"]*)\"のoptionが選択されている$")
    public void のoptionが選択されている(String selectedNumber) throws Throwable {
        if ("".equals(selectedNumber)) {
//            driver.clickRadioButton();
        } else {
            throw new PendingException();
        }
    }

    @Given("^adviceに \"([^\"]*)\" が入力されている$")
    public void adviceに_が入力されている(String advice) throws Throwable {
        driver.setTextField("advice", advice);
    }

    @When("^Addボタンを押す$")
    public void addボタンを押す() throws Throwable {
        driver.clickButton("add_button");
    }

    @Then("^\"([^\"]*)\"というメッセージが表示される$")
    public void というメッセージが表示される(String errorMessage) throws Throwable {
        driver.expectElementWithIdToContainText("message", errorMessage);
    }

    @Given("^(\\d+)のoptionが選択されている$")
    public void のoptionが選択されている(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
