package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Category;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class WrongQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^Given Add a question \"([^\"]*)\" with dummy options and advice \"([^\"]*)\"$")
    public void given_Add_a_question_with_dummy_options_and_advice(String arg1, String arg2)  {
        new QuestionBuilder()
            .aQuestion(arg1, arg2, String.valueOf(Category.SCRUM.getId()))
            .withWrongOption("Food")
            .withWrongOption("Drink")
            .withWrongOption("Country")
            .withWrongOption("Animal")
            .withCorrectOption("None of the above")
            .please();
    }

    @When("^問題に正解する$")
    public void 問題に正解する() {
    }

    @When("^問題に不正解する$")
    public void 問題に不正解する() {
    }

    @Then("^問題が非表示される$")
    public void 問題が非表示される() {
    }

    @Then("^問題が表示される$")
    public void 問題が表示される() {
    }

    @When("^(\\d+)時間が経過する$")
    public void 時間が経過する(int arg1) {
    }
}
