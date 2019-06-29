package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.onlinetest.Question;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.Map;

public class AdminApprovePublicQuestionSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    String questionId;

    @Given("^未承認の問題がある$")
    public void 未承認の問題がある(Map<String, String> questionMap){
        Question q = new QuestionBuilder()
                .aQuestion(questionMap.get("description"),null, new CategoryBuilder().categoryByName("Scrum"))
                .withCorrectOption(questionMap.get("option1"))
                .withWrongOption(questionMap.get("option2"))
                .please();
        questionId = q.getStringId();
    }

    @When("^Adminが承認する$")
    public void adminが承認する(){
        site.visit("onlinetest/question_list.jsp");
        driver.click("#" + ("approve-" + questionId));
    }

    @Then("^問題が承認済みになる$")
    public void 問題が承認済みになる(){
        driver.expectElementNotToExist("#approve-" + questionId);
    }
}
