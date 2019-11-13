package cucumber.steps;

import com.odde.snowball.model.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class ResetQuestionFrequencyStep {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private final String login_url = site.baseUrl() + "login.jsp";
    private final User user = createUser("john@example.com");
    private final String password = "abcd1002";

    private User createUser(String email) {
        User user = new User("john@example.com");
        user.setupPassword(password);
        user.save();

        return user;
    }

    @Given("There is a logged in user")
    public void there_is_a_logged_in_user() {
        driver.visit(login_url);
        driver.expectPageToContainText("Login Massive Mailer");
        driver.setTextField("email", user.getEmail());
        driver.setTextField("password", password);
        driver.click("#login");
    }

    @Given("There are {int} questions")
    public void there_are_questions(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The user start the practice")
    public void the_user_start_the_practice() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The user answer {int} correctly")
    public void the_user_answer_correctly(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The user answer {int} incorrectly")
    public void the_user_answer_incorrectly(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("The user has seen the {string}")
    public void the_user_has_seen_the(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("User start the practice again")
    public void user_start_the_practice_again() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("User only sees the same question that was answer incorrectly")
    public void user_only_sees_the_same_question_that_was_answer_incorrectly() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
