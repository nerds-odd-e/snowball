package cucumber.steps;

import com.odde.snowball.factory.QuestionBuilder;
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
}

