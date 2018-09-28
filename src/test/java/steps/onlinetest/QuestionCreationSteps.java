package steps.onlinetest;

import com.odde.massivemailer.model.Question;
import com.odde.massivemailer.model.QuestionOption;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionCreationSteps {
    private MassiveMailerSite site = new MassiveMailerSite();
    private WebDriverWrapper driver = site.getDriver();

    @Given("^no question registered$")
    public void no_question_registered() throws Throwable {
        QuestionOption.deleteAll();
        Question.deleteAll();
    }

    @When("^Push submit with question body \"([^\"]*)\" and question advice \"([^\"]*)\" with the following answers <body> and the first answers is correct$")
    public void push_submit_with_question_body_and_question_advice_with_the_following_answers_body_correct(String body, String advice, DataTable answersTable) throws Throwable {
        String url = "question/creation";

        site.visit(url);
        driver.setDropdownValue("category", "Technical practice");
        driver.setTextField("body", body);
        driver.setTextField("advice", advice);


        List<String> answers = answersTable.asList(String.class);
        answers.forEach(answer -> driver.setTextField(answer, answer));

        driver.clickButton("save_button");

        Question question = (Question) Question.find("body = " + body +" AND advice = " + advice).get(0);

        List<Question> questions = Question.find("body = " + body + " AND advice = " + advice);

        assertEquals(1, questions.size());
        Question latestQuestion = questions.get(questions.size() - 1);
        assertEquals(body, latestQuestion.get("body"));
        assertEquals(advice, latestQuestion.get("advice"));

        assertEquals(6, QuestionOption.find("question_id = " + question.get("id")).size());
    }

    @Then("^Display registered contents with (\\d+) answers$")
    public void display_registered_contents_with_answers(int numOfAnswers) throws Throwable {
        final WebElement question = driver.findElements(By.className("question")).get(0);
        assertEquals("Technical practice", question.findElement(By.className("category")).getText());
        assertEquals("body", question.findElement(By.className("body")).getText());
        final WebElement answers = question.findElement(By.className("answers"));
        final List<WebElement> answerList = answers.findElements(By.cssSelector("li"));
        assertEquals("answer_1", answerList.get(0).getText());
        assertEquals("answer_2", answerList.get(1).getText());
        assertEquals("answer_3", answerList.get(2).getText());
        assertEquals("answer_4", answerList.get(3).getText());
        assertEquals("answer_5", answerList.get(4).getText());
        assertEquals("answer_6", answerList.get(5).getText());
    }

    @Then("^Reset form$")
    public void reset_form() throws Throwable {
        final WebElement form = driver.findElements(By.tagName("form")).get(0);
        assertEquals("", form.findElement(By.name("body")).getText());
    }
}
