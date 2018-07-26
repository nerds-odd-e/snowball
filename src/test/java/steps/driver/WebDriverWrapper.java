package steps.driver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebDriverWrapper {
    private WebDriver driver;

    public WebDriverWrapper() {
        if (System.getProperty("webdriver").equals("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);
        }
        else {
            DesiredCapabilities dcap = new DesiredCapabilities();
            String[] phantomArgs = new  String[] {
                    "--webdriver-loglevel=NONE"
            };
            dcap.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
            driver = new PhantomJSDriver(dcap);
            Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

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

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public UiElement findElementByName(String name) {
        return new SeleniumWebElement(driver.findElement(By.name(name)));
    }

    public void setTextField(String field_name, String text) {
        UiElement e = findElementByName(field_name);
        e.sendKeys(text);
    }

    public void clickButton(String button_id) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id(button_id))).click();
    }

    public void clickButtonByName(String button_name) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.name(button_name))).click();
    }

    public void clickXPath(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public void clickById(String id) {
        driver.findElement(By.id(id)).click();
    }

    public UiElement findElementById(String id) {
        return new SeleniumWebElement(driver.findElement(By.id(id)));
    }

    public void expectAlert(String msg) {
        getWait().until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertEquals(msg, alert.getText());
        alert.accept();
    }

    public void expectRedirect(String url) {
        getWait().until(ExpectedConditions.urlContains(url));
    }

    public void pageShouldContain(String text) {
        String bodyText = getBodyText();
        assertThat(bodyText, containsString(text));
    }

    public String getBodyText() {
        return driver.findElement(By.tagName("body")).getText();
    }

    public void expectElementWithIdToContainText(String id, String text) {
        String actualText = findElementById(id).getText();
        assertTrue("Text not found! actual: "+ actualText+ ", expected:`" + text + "` But got: `" + getBodyText() + "`", actualText.contains(text));
    }

    public void expectElementWithIdToContainValue(String id, int value) {
        assertEquals(Integer.toString(value), findElementById(id).getText());
    }

    public void expectElementWithIdGreaterThanOrEqualsValue(String id, int value) {
        assertTrue(Integer.parseInt(findElementById(id).getText()) >= value);
    }

    public void expectPageToContainExactlyNElements(String text, int count) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'"+text+"')]"));
        assertEquals(elements.size(), count);
    }

    public void clickUpload() {
        UiElement uploadBtn = findElementById("batchFile");
        uploadBtn.sendKeys(System.getProperty("java.io.tmpdir") + "/contactsUploadTest.csv");
    }

    public int countElementWithClass(String cssClass) {
        return driver.findElements(By.className(cssClass)).size();
    }

    public String getElementMarginWithClass(String cssClass) { return driver.findElement(By.className(cssClass)).getCssValue("margin-left"); }

    public void setDropdownByText(String dropdownName, String text) {
        Select dropdown = new Select(getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name(dropdownName))));
        dropdown.selectByVisibleText(text);
    }

    public void takeScreenShot() {

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDropdownValue(String dropdownName, String text) {
        Select dropdown = new Select(getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name(dropdownName))));
        dropdown.selectByValue(text);
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, 10);
    }

    public void waitforElement(String domId) {
        getWait().until(presenceOfElementLocated(By.id(domId)));
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }
}

