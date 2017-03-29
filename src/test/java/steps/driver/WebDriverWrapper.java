package steps.driver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebDriverWrapper {
    private WebDriver driver;

    public WebDriverWrapper() {
        if (System.getProperty("webdriver").equals("chrome"))
            driver = new ChromeDriver();
        else {
            DesiredCapabilities dcap = new DesiredCapabilities();
            String[] phantomArgs = new  String[] {
                    "--webdriver-loglevel=NONE"
            };
            dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
            driver = new PhantomJSDriver(dcap);
            Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
            driver.manage().window().maximize();
        }

    }
    public void visit(String url) {
        driver.get(url);
    }

    public void closeAll() {
        driver.close();
        driver.quit();
    }

    public void isAtURL(String url) {
        assertTrue(driver.getCurrentUrl().equals(url));
    }

    public UiElement findElementByName(String name) {
        return new SeleniumWebElement(driver.findElement(By.name(name)));
    }

    public void setTextField(String field_name, String text) {
        UiElement e = findElementByName(field_name);
        e.sendKeys(text);
    }

    public void setDropdownValue(String dropdownName, String text) {
        Select dropdown = new Select(driver.findElement(By.name(dropdownName)));
        dropdown.selectByValue(text);
    }

    public void clickButton(String button_name) {
        UiElement e = findElementById(button_name);
        e.click();
    }

    public void clickXPath(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public UiElement findElementById(String id) {
        return new SeleniumWebElement(driver.findElement(By.id(id)));
    }

    public void expectAlert(String msg) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertEquals(msg, alert.getText());
        alert.accept();
    }

    public void expectRedirect(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.urlContains(url));
    }

    public void pageShouldContain(String text) {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue("Text not found!", bodyText.contains(text));
    }

    public void expectElementWithIdToContainText(String id, String text) {
        String actualText = findElementById(id).getText();
        assertTrue("Text not found! actual: "+ actualText+ ", expected:" + text, actualText.contains(text));
    }

    public void expectElementWithIdToContainValue(String id, int value) {
        assertEquals(Integer.toString(value), findElementById(id).getText());
    }

    public void expectElementWithIdGreaterThanOrEqualsValue(String id, int value) {
        assertTrue(Integer.parseInt(findElementById(id).getText()) >= value);
    }

    public void expectElementWithIdToContainTextInXSeconds(String id, String value, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.attributeToBe(By.id(id), "innerHTML", value));
    }

    public void expectPageToContainExactlyNElements(String text, int count) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'"+text+"')]"));
        assertEquals(elements.size(), count);
    }

    public int countElementWithClass(String cssClass) {
        return driver.findElements(By.className(cssClass)).size();
    }

    public String getElementMarginWithClass(String cssClass) { return driver.findElement(By.className(cssClass)).getCssValue("margin-left"); }
}

