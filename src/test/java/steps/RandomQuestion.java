package steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RandomQuestion {

    @When("^Student choose a correct answer for (\\d+) times \"([^\"]*)\" \"([^\"]*)\"$")
    public void student_choose_a_correct_answer_for_times(int arg1, String arg2, String arg3) throws Throwable {
    }

    @Then("^System displayed EndOfTest page$")
    public void system_displayed_EndOfTest_page() throws Throwable {
    }

//    @Given("^Student as user$")
//    public void student_as_user() throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @Given("^Student want to take random test$")
//    public void student_want_to_take_random_test() throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @Given("^System displayed random test question page\\.$")
//    public void system_displayed_random_test_question_page() throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @When("^Student choose a correct answer\\.$")
//    public void student_choose_a_correct_answer() throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @When("^Student pressed \"([^\"]*)\" button\\.$")
//    public void student_pressed_button(String arg1) throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @Then("^System displayed next question which choose Randomly$")
//    public void system_displayed_next_question_which_choose_Randomly() throws Throwable {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
}
