package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
import com.odde.snowball.model.onlinetest.Category;
import cucumber.api.java.en.Given;
import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class UiCommonSteps {

    private final SnowballSite site = new SnowballSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^I'm on the admin dashboard$")
    public void メニューを選択できる画面に遷移する() {
        site.visit("admin/dashboard.jsp");
    }

    @Given("^there is one question exist in the system$")
    public void 問題が存在している() {
        new QuestionBuilder()
                .aQuestion("testDescription", "testAdvice", Category.getIdByName("Scrum"))
                .withWrongOption("Food")
                .withWrongOption("Drink")
                .withWrongOption("Country")
                .withWrongOption("Animal")
                .withCorrectOption("None of the above")
                .please();
    }
}

