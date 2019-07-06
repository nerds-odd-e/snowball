package cucumber.steps.driver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.seleniumhq.jetty9.util.IO.copyFile;

public class WebDriverWrapper {
    private final WebDriver driver;

    public Optional<String> executeJavaScript(String script) {
        return Optional.ofNullable(((JavascriptExecutor) driver).executeScript(script))
                .filter(object -> object instanceof String)
                .map(object -> (String) object);
    }

    public WebDriverWrapper() {
        System.setProperty("webdriver.chrome.args", "--disable-logging");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--silent");
        chromeOptions.addArguments("--start-maximized");
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    void closeAll() {
        driver.close();
        driver.quit();
    }

    public void expectTitleToBe(String title) {
        assertThat(driver.getTitle()).as("page title").isEqualTo(title);
    }

    public void visit(String url) {
        driver.get(url);
    }

    public void expectURLToContain(String s) {
        assertThat(driver.getCurrentUrl()).as("current url").contains(s);
    }

    public void setTextField(String field_name, String text) {
        waitForElementByName(field_name).sendKeys(text);
    }

    public void clickButtonByName(String button_name) {
        waitForElementByName(button_name).click();
    }

    public void click(String smartSelector) {
        getWebElementBySmartSelector(smartSelector).click();
    }

    public void expectElementToExist(String smartSelector) {
        getWebElementBySmartSelector(smartSelector);
    }

    public void expectElementToExist(String smartSelector, int count) {
        waitUntil(ExpectedConditions.numberOfElementsToBe(By.cssSelector(smartSelector), count));
    }

    public void expectElementNotToExist(String smartSelector) {
        assertThat(driver.findElements(By.cssSelector(smartSelector))).as("element by selector `%s`", smartSelector)
                .isEmpty();
    }

    public void expectNoElementToContainText(String smartSelect, String text) {
        waitUntil(not(textToBePresentInElementLocated(By.cssSelector(smartSelect), text)));
    }

    public void expectElementToContainText(String smartSelect, String text) {
        waitUntil(textToBePresentInElementLocated(By.cssSelector(smartSelect), text));
    }

    public void expectPageToContainText(String text) {
        expectElementToContainText("body", text);
    }

    public void expectPageNotToContainText(String text) {
        expectNoElementToContainText("body", text);
    }

    public void expectPageToContainExactlyNElements(String text, int count) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'" + text + "')]"));
        assertThat(elements).hasSize(count);
    }

    public void expectElementsToHaveTexts(String cssSelector, List<String> optionTexts) {
        List<WebElement> elements = findElements(cssSelector);
        List<String> actualTexts = elements.stream().map(WebElement::getText).collect(Collectors.toList());
        assertThat(actualTexts).containsExactlyElementsOf(optionTexts);
    }

    public void forEachElement(String cssSelector, Consumer<WebElement> action) {
        findElements(cssSelector).forEach(action);
    }

    public void expectAlert(String msg) {
        waitUntil(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).as("alert message").isEqualTo(msg);
        alert.accept();
    }

    public void expectRedirect(String url) {
        waitUntil(ExpectedConditions.urlContains(url));
    }

    public void clickUpload(String smartSelector, String filename) {
        getWebElementBySmartSelector(smartSelector).sendKeys(filename);
    }

    public void selectDropdownByText(String dropdownName, String text) {
        getSelect(dropdownName).selectByVisibleText(text);
    }

    public void selectDropdownByValue(String dropdownName, String text) {
        getSelect(dropdownName).selectByValue(text);
    }

    public void clickRadioButton(String text) {
        getInputBySelectorAndText("input", text).click();
    }

    public void clickCheckBox(String text) {
        getInputBySelectorAndText("input", text).click();
    }

    public void expectRadioButtonWithText(String optionText) {
        getInputBySelectorAndText("input[type='radio']", optionText);
    }

    public WebDriver.Navigation getNavigate() {
        return this.driver.navigate();
    }

    public void takeScreenshot(String name) {
        ChromeDriver chromeDriver = (ChromeDriver) driver;
        File source = chromeDriver.getScreenshotAs(OutputType.FILE);
        String dest = name + ".png";
        File destination = new File(dest);
        try {
            Files.createDirectories(Paths.get(destination.getParent()));
            copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <V> V waitUntil(Function<WebDriver, V> isTrue) {
        return new WebDriverWait(driver, 2).until(isTrue);
    }

    private WebElement getWebElementBySmartSelector(String smartSelector) {
        return waitForElement(By.cssSelector(smartSelector));
    }

    private WebElement waitForElement(By by) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private WebElement getInputBySelectorAndText(String selector, String text) {
        return getWebElementStreamOfParents(selector)
                .filter(e -> e.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        String.format("Can not find element with selector `%s` and text `%s`, but found %s",
                                selector,
                                text,
                                getWebElementStreamOfParents(selector).map(WebElement::getText).collect(Collectors.joining(","))
                        )))
                .findElement(By.cssSelector(selector));
    }

    private Stream<WebElement> getWebElementStreamOfParents(String selector) {
        return findElements(selector).stream().map(e -> e.findElement(By.xpath("./..")));
    }

    private List<WebElement> findElements(String selector) {
        return waitUntil(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(selector)));
    }

    private Select getSelect(String dropdownName) {
        return new Select(waitForElementByName(dropdownName));
    }

    private WebElement waitForElementByName(String name) {
        return waitForElement(By.name(name));
    }
}
