package steps;

import com.odde.massivemailer.factory.QuestionBuilder;
import com.odde.massivemailer.model.onlinetest.Category;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiCommonSteps {

    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @Given("^I'm on the admin dashboard$")
    public void メニューを選択できる画面に遷移する() {
        site.visit("admin/dashboard.jsp");
    }

    @Given("^there is one question exist in the system$")
    public void 問題が存在している() throws Throwable {
        new QuestionBuilder()
                .aQuestion("testDescription", "testAdvice", String.valueOf(Category.getIdByName("Scrum")))
                .withWrongOption("Food")
                .withWrongOption("Drink")
                .withWrongOption("Country")
                .withWrongOption("Animal")
                .withCorrectOption("None of the above")
                .please();
    }
}

