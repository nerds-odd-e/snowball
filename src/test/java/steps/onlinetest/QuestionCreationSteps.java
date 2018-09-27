package steps.onlinetest;

import com.odde.massivemailer.model.Question;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionCreationSteps {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    // After make a question the form is reset
    @Given("^no question registered$")
    public void no_question_registered() throws Throwable {
        Question.deleteAll();
        assertTrue(Question.create("body", "body",
                "advice", "advice").saveIt());


        site.addQuestionPage();
    }

    @When("^Push submit with required fields$")
    public void push_submit_with_required_fields() throws Throwable {
        String url =  "question/creation";
        site.visit(url);
        driver.setTextField("body", "body2");
        driver.setTextField("advice", "advice");
        driver.setTextField("answer_1", "answer_1");
        driver.setTextField("answer_2", "answer_2");
        driver.clickButton("save_button");

        assertTrue(Question.find("body = 'body2' AND advice = 'advice'").size() > 0);
    }

    @Then("^Display registered contents$")
    public void display_registered_contents() throws Throwable {
        final WebElement question = driver.findElements(By.className("question")).get(0);
        assertEquals("body", question.findElement(By.className("body")).getText());
        // FIXME コメントアウトハズス
        //        final WebElement answers = question.findElement(By.className("answers"));
//        final List<WebElement> answerList = answers.findElements(By.className("answer"));
//        assertEquals("answer_1", answerList.get(0).getText());
//        assertEquals("answer_2", answerList.get(1).getText());
    }

    @Then("^Reset form$")
    public void reset_form() throws Throwable {
        final WebElement form = driver.findElements(By.tagName("form")).get(0);
        assertEquals("", form.findElement(By.name("body")).getText());
    }
}
