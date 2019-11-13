package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Question;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.ArrayList;
import java.util.List;

public class ResetQuestionFrequencyStep {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String login_url = site.baseUrl() + "login.jsp";
    private final User user = createUser("john@example.com");
    private final String password = "abcd1002";
    private int numberOfCorrectAnsweredQuestion;
    private List<Question> questions = new ArrayList<Question>();

    private User createUser(String email) {
        User user = new User("john@example.com");
        user.setupPassword(password);
        user.save();

        return user;
    }



    @Given("The user start the practice")
    public void the_user_start_the_practice() {
        driver.click("#start_practice_button");
    }


    @Given("The user answer {int} incorrectly")
    public void the_user_answer_incorrectly(Integer incorrectCount) {
        for (int i = 0; i < incorrectCount; ++i) {
            driver.clickRadioButton("Food");
            driver.click("#answer");
            driver.click("#next");
        }
    }

    @Given("The user has seen the {string}")
    public void the_user_has_seen_the(String text) {
        driver.expectPageToContainText(text);
    }

    @When("User start the practice again")
    public void user_start_the_practice_again() {
        driver.click("#home");
        driver.expectURLToContain("/dashboard");
        driver.click("#start_practice_button");
    }

    @Then("User only sees the {string} that was incorrect")
    public void user_only_sees_the_same_question_that_was_answer_incorrectly(String text) {
        driver.expectPageToContainText(text);
    }
}
