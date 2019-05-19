package steps;

import com.odde.massivemailer.model.User;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.mock.web.MockHttpServletRequest;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CategoryAddSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();
    private User user;
    private MockHttpServletRequest request = new MockHttpServletRequest();

    @When("^ユーザーがログインする$")
    public void ユーザーがログインする() {
        request.getSession().setAttribute("user_id", user.getLongId());
    }

    @Then("^カテゴリ追加ボタンが見える$")
    public void カテゴリ追加ボタンが見える() {
        assertTrue(driver.findElementById("add_category_button").getText().contains("Add Category"));
    }


    @When("^click the add category button")
    public void カテゴリ追加ボタンを押す() {
        driver.clickButton("add_category_button");
        assertEquals("Add Category", driver.getCurrentTitle());
    }

    @When("^add a new category$")
    public void 新しいカテゴリを入力する() {
        driver.setTextField("category_name","新カテゴリ");
        driver.clickButton("add_category");
    }

    @Then("^it should be redirected to the add question page$")
    public void 問題作成画面に遷移する() {
        assertEquals("Add Question", driver.getCurrentTitle());
    }

    @Then("^I can  select the new category$")
    public void 新しいカテゴリが選択できる() {
        assertTrue(driver.findElementById("categoryList").getText().contains("新カテゴリ"));
    }


}
