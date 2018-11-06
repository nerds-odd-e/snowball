package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddQuestionSteps {
    @Given("^Add Questionを開いている$")
    public void AddQuestion() {
        throw new PendingException();
    }

    @Given("^Descriptionに\"([^\"]*)\" が入力されている$")
    public void descriptionに_が入力されている(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^option(\\d+)に\"([^\"]*)\"が入力されている$")
    public void option_に_が入力されている(int arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^のoptionが選択されている$")
    public void のoptionが選択されている() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^adviceに \"([^\"]*)\" が入力されている$")
    public void adviceに_が入力されている(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Addボタンを押す$")
    public void addボタンを押す() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^\"([^\"]*)\"というメッセージが表示される$")
    public void というメッセージが表示される(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^(\\d+)のoptionが選択されている$")
    public void のoptionが選択されている(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
