package steps.driver;

public interface UiElement {
    void sendKeys(String keys);

    void submit();

    String getText();

    String getAttribute(String attrName);

    void click();
}
