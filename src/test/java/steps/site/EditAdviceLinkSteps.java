package steps.site;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import steps.driver.WebDriverWrapper;

import static org.junit.Assert.assertTrue;

public class EditAdviceLinkSteps {
    private final MassiveMailerSite site = new MassiveMailerSite();
    private final WebDriverWrapper driver = site.getDriver();

    @And("^アドバイスリンクに\"([^\"]*)\"と入力する$")
    public void アドバイスリンクにと入力する(String link) throws Throwable {
        driver.setTextField("link", link);
    }

    @Then("^Update Advice画面に戻ってきてカテゴリが\"([^\"]*)\"でアドバイスリンクが\"([^\"]*)\"になってる$")
    public void updateAdvice画面に戻ってきてカテゴリがでアドバイスリンクがになってる(String category, String link) throws Throwable {
        assertTrue(driver.getBodyText().contains(link));
    }
}
