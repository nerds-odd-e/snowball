package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
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
        if (!"".equals(selectedNumber)) {
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

    class Result {
        boolean found;
    }

    @Then("^\"([^\"]*)\"というメッセージが表示される$")
    public void というメッセージが表示される(String errorMessage) throws Throwable {
        Result r = new Result();
        driver.findElements(By.className("alert")).forEach(el -> {
            if (el.getText().contains(errorMessage)) {
                r.found = true;
            }
        });
        Assert.assertTrue(r.found);
    }

    @Given("^(\\d+)のoptionが選択されている$")
    public void のoptionが選択されている(int arg1) throws Throwable {
        throw new PendingException();
    }
}
