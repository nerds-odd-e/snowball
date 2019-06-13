package steps;

import com.odde.snowball.model.User;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.mock.web.MockHttpServletRequest;
import steps.driver.WebDriverWrapper;
import steps.site.SnowballSite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CategoryAddSteps {
    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();
    private User user;
    private MockHttpServletRequest request = new MockHttpServletRequest();

    @When("^ユーザーがログインする$")
    public void ユーザーがログインする() {
        request.getSession().setAttribute("user_id", user.getId());
    }

    @Then("^カテゴリ追加ボタンが見える$")
    public void カテゴリ追加ボタンが見える() {
        driver.expectElementToContainText("#add_category_button", "Add Category");
    }


    @When("^click the add category button")
    public void カテゴリ追加ボタンを押す() {
        driver.click("#add_category_button");
        driver.expectTitleToBe("Add Category");
    }

    @When("^add a new category$")
    public void 新しいカテゴリを入力する() {
        driver.setTextField("category_name","新カテゴリ");
        driver.click("#add_category");
    }

    @Then("^it should be redirected to the add question page$")
    public void 問題作成画面に遷移する() {
        driver.expectTitleToBe("Add Question");
    }

    @Then("^I can  select the new category$")
    public void 新しいカテゴリが選択できる() {
        driver.expectElementToContainText("#categoryList", "新カテゴリ");
    }


}
