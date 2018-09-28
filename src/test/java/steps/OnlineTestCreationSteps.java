package steps;

import com.odde.massivemailer.model.OnlineTest;
import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.QuestionOption;
import com.odde.massivemailer.service.OnlineTestService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class OnlineTestCreationSteps {
    private OnlineTestService onlineTestService;

    @Given("^There are \"([^\"]*)\" questions in the system$")
    public void there_are_questions_in_the_system(int n) throws Throwable {
        QuestionOption.deleteAll();
        Question.deleteAll();
        IntStream.rangeClosed(1, n).forEach(i -> {
            String category = null;
            switch (i % 4) {
                case 0:
                    category = "Scrum";
                    break;
                case 1:
                    category = "Technical practice";
                    break;
                case 2:
                    category = "Organization";
                    break;
                case 3:
                    category = "Scaling";
                    break;
            }
            new Question(String.valueOf(i), new ArrayList<>(), "advice", category, null).saveIt();
        });
    }

    @Given("^More than one question registered in all categories$")
    public void more_than_one_question_registered_in_all_categories() throws Throwable {
    }

    @When("^I start an online test$")
    public void i_start_an_online_test() throws Throwable {
        onlineTestService = new OnlineTestService();
    }

    @Then("^An online test with \"([^\"]*)\" questions is generated$")
    public void an_online_test_with_questions_is_generated(int m) throws Throwable {
        OnlineTest onlineTest = onlineTestService.generate();
        assertEquals(m, onlineTest.getQuestions().size());
        int categorySize = onlineTest.getQuestions().stream().map(Question::getCategory).collect(Collectors.toSet()).size();
        assertEquals(4, categorySize);
    }

    @Then("^The problem in the test includes four categories$")
    public void the_problem_in_the_test_includes_four_categories() throws Throwable {
    }


}
