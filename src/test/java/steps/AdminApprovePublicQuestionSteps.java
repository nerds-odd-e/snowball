package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Question;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.Map;

public class AdminApprovePublicQuestionSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    String questionId;

    @Given("^未承認の問題がある$")
    public void 未承認の問題がある(DataTable question){
        Map<String, String> questionMap = question.asMap(String.class, String.class);
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
        driver.clickButton("approve-" + questionId);
    }

    @Then("^問題が承認済みになる$")
    public void 問題が承認済みになる(){
        Assert.assertEquals(0, driver.findElements(By.id("approve-" + questionId)).size());
    }
}
