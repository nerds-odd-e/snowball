package gradle.cucumber.driver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebDriverWrapper {
     private WebDriver driver = new FirefoxDriver();

     public void visit(String url) {
         driver.get(url);
     }

    public void closeAll() {
        driver.close();
        driver.quit();
    }

    public UiElement findElementByName(String name) {
        return new SeleniumWebElement(driver.findElement(By.name(name)));
    }

    public void text_field(String field_name, String text) {
        UiElement e = findElementByName(field_name);
        e.sendKeys(text);
    }

    public void click_button(String button_name) {
        UiElement e = findElementById(button_name);
        e.click();

    }

    private UiElement findElementById(String id) {
        return new SeleniumWebElement(driver.findElement(By.id(id)));
    }

    public void expectAlert(String msg) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertEquals(msg, alert.getText());
        alert.accept();
    }

    public void pageShouldContain(String text) {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue("Text not found!", bodyText.contains(text));
    }

    public void pageShouldContainElementTagWithAttribute(String tag, String attr, String attrValue) {
        List<WebElement> listTextbox = driver.findElements(By.tagName(tag));
        for(int i = 0; i < listTextbox.size(); i++) {
            if(listTextbox.get(i).getAttribute(attr).contains(attrValue)) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(tag + " not found!", false);
    }

    public void pageShouldContainElementTagWithText(String tag, String text) {
        List<WebElement> listTextbox = driver.findElements(By.tagName(tag));
        for(int i = 0; i < listTextbox.size(); i++) {
            if(listTextbox.get(i).getText().contains(text)) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(tag + " not found!", false);
    }

    public void expectElement(String name, String text) {
        String elementText = findElementById(name).getText();
        assertTrue(text, elementText.contains(text));
    }
}
