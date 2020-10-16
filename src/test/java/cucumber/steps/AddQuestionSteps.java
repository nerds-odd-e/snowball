package cucumber.steps;

import com.odde.snowball.model.User;
import com.odde.snowball.model.onlinetest.Category;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

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

    @Given("^is-publicをチェックする$")
    public void isPublicをチェックする() {
        driver.clickCheckBoxById("is-public");
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

    @Then("^User should see questions and options in question list page with correct one highlighted$")
    public void userShouldSeeQuestionsAndOptionsInQuestionListPage(Map<String, String> questionMap) {
        site.visit("onlinetest/question_list.jsp");
        driver.expectTitleToBe("Question List");
        driver.expectPageToContainText(questionMap.get("description"));
        driver.expectPageToContainText(questionMap.get("option1"));
        driver.expectPageToContainText(questionMap.get("option2"));
        driver.expectElementToExist("#option5row");
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


    private final String login_url = site.baseUrl() + "login.jsp";
    private void visitLoginPage() {
        driver.visit(login_url);
        driver.expectPageToContainText("Login Massive Mailer");
    }

    @Given("{string}な質問が{string}によって登録されている")
    public void な質問が_によって登録されている(String isPublic, String user) {
        visitLoginPage();
        driver.setTextField("email", user.toLowerCase() + "@hogehoge.com");
        driver.setTextField("password", "11111111");
        driver.click("#login");

        AddQuestion();

        descriptionに_が入力されている("What is scrum?");

        option_に_を入力する(1, "Scrum is Rugby");
        option_に_を入力する(2, "Scrum is Baseball");

        を回答として選択済み("option1");

        if("public".equals(isPublic)) {
            isPublicをチェックする();
        }

        addボタンを押す();
    }

    @When("日本語の質問を登録する")
    public void 日本語の質問を登録する() {
        thereIsAQuestionCategory("スクラム");

        AddQuestion();

        descriptionに_が入力されている("スプリントバックログの分け方として正しいものはどれか");

        option_に_を入力する(1, "設計・開発・テストフェーズでタスクを分ける");
        option_に_を入力する(2, "Given・When・Thenでタスクを分ける");
        option_に_を入力する(3, "顧客価値に従ってタスクを分ける");
        option_に_を入力する(4, "チームごとに最適を模索する");
        を回答として選択済み("option4");
        adviceに_を入力する("実践して探してみよう!");

        addボタンを押す();
    }

    @Then("質問リストの質問文と設問とアドバイスが日本語で表示される")
    public void 質問リストの質問文と設問とアドバイスが日本語で表示される() {
        driver.takeScreenshot("tmp/質問リストの質問文と設問とアドバイスが日本語で表示される");
        driver.expectPageToContainText("スプリントバックログの分け方として正しいものはどれか");
    }
}
