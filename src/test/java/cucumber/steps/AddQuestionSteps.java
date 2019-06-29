package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.onlinetest.Category;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddQuestionSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^there is a question category \"([^\"]*)\"$")
    public void thereIsAQuestionCategory(String name) {
        Category.create(name);
    }

    @Given("^Add Questionを開いている$")
    public void AddQuestion() {
        site.visit("onlinetest/add_question.jsp");
        driver.expectTitleToBe("Add Question");
    }

    @Given("^Descriptionに\"([^\"]*)\" を入力する$")
    public void descriptionに_が入力されている(String description) {
        driver.setTextField("description", description);
    }

    @Given("^Typeを\"([^\"]*)\" を選択する$")
    public void typeを_を選択する(String type) {
        driver.selectDropdownByText("type", type);
    }

    @Given("^option(\\d+)に\"([^\"]*)\"を入力する$")
    public void option_に_を入力する(int optionNumber, String optionName) {
        driver.setTextField("option" + optionNumber, optionName);
    }

    @Given("^checkbox(\\d+)に\"([^\"]*)\"を入力する$")
    public void checkbox_に_を入力する(int checkboxNumber, String checkboxName) {
        driver.setTextField("checkbox" + checkboxNumber, checkboxName);
    }

    @Given("^\"([^\"]*)\"に\"([^\"]*)\"を入力する$")
    public void _に_を入力する(String elementName, String value) {
        driver.setTextField(elementName, value);
    }

    @Given("^\"([^\"]*)\"番目のoptionを選択する$")
    public void 番目のoptionを選択する(String selectedNumber) {
        if (!"".equals(selectedNumber)) {
            driver.click("#" + ("option" + selectedNumber));
        }
    }

    @Given("^adviceに \"([^\"]*)\" を入力する$")
    public void adviceに_を入力する(String advice) {
        driver.setTextField("advice", advice);
    }

    @When("^Addボタンを押す$")
    public void addボタンを押す() {
        driver.click("#add_button");
    }

    @Given("^Question added$")
    public void questionAdded(Map<String, String> questionMap) {
        new QuestionBuilder()
                .aQuestion(questionMap.get("description"),null, new CategoryBuilder().categoryByName("Scrum"))
                .withCorrectOption(questionMap.get("option1"))
                .withWrongOption(questionMap.get("option2"))
                .please();
        site.visit("onlinetest/question_list.jsp");
    }

    @Then("^User should see questions and options in question list page with correct one highlighted$")
    public void userShouldSeeQuestionsAndOptionsInQuestionListPage(Map<String, String> questionMap) {
        driver.expectTitleToBe("Question List");
        driver.expectPageToContainText(questionMap.get("description"));
        driver.expectPageToContainText(questionMap.get("option1"));
        driver.expectPageToContainText(questionMap.get("option2"));
        driver.expectElementToExist("#option1row");
    }

    @Then("^\"([^\"]*)\"というメッセージが表示される$")
    public void というメッセージが表示される(String errorMessage) {
        driver.expectElementToContainText(".alert", errorMessage);
    }

    @Given("^Option(\\d+) に\"([^\"]*)\"文字を入力する$")
    public void option_に_文字を入力する(int optionNumber, int optionCount) {
        String temporaryText = StringUtils.repeat("a", optionCount);
        driver.setTextField("option" + optionNumber, temporaryText);
    }

    @Given("^Description に \"([^\"]*)\" 文字を入力する$")
    public void description_に_文字を入力する(int descriptionLength) {
        String temporaryText = StringUtils.repeat("a", descriptionLength);
        driver.setTextField("description", temporaryText);
    }


    @Given("^\"([^\"]*)\"を回答として選択済み$")
    public void を回答として選択済み(String optionId) {
        driver.click("#" + optionId);
    }

    @Given("^adviceが に\"([^\"]*)\"文字を入力する$")
    public void adviceが_に_文字を入力する(int adviceTextCount) {
        String temporaryText = StringUtils.repeat("a", adviceTextCount);
        driver.setTextField("advice", temporaryText);
    }

    @Given("^advice に\"([^\"]*)\" を入力する$")
    public void advice_に_を入力する(String advice) {
        driver.setTextField("advice", advice);
    }

    @When("^OnlineTestを開始する$")
    public void onlinetestを開始する() {
        site.visit("onlinetest/launchQuestion");
    }

    @Then("^\"([^\"]*)\"という問題が出題される$")
    public void という問題が出題される(String description) {
        driver.expectElementToContainText("#description", description);
    }

    @Then("^option(\\d+)に\"([^\"]*)\"が表示される$")
    public void option_に_が表示される(int optionNumber, String optionName) {
        driver.expectPageToContainText(optionName);
    }

    @Then("^\"([^\"]*)\"を回答として選択する$")
    public void を回答として選択する(String optionName) {
        driver.click("#" + optionName);
    }

    @Then("^Answerボタンを押す$")
    public void answerボタンを押す() {
        driver.click("#answer");
    }

    @Then("^EndOfTheTestが表示される$")
    public void endofthetestが表示される() {
        driver.expectTitleToBe("End Of Test");
    }

    @Then("^カテゴリーに \"([^\"]*)\" が表示される$")
    public void カテゴリーに_が表示される(String description) {
        driver.expectElementToContainText("#category", description);
    }

    @Given("^カテゴリーとして\"([^\"]*)\"を選択する$")
    public void カテゴリーとして_を選択する(String category) {
        driver.selectDropdownByText("category", category);
    }
}
