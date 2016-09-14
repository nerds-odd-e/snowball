package gradle.cucumber.driver;

public class SingleDrive {
    private static WebDriverWrapper driver;

    public static WebDriverWrapper getDriver() {
        if (driver == null)
            driver = new WebDriverWrapper();
        return driver;
    }

    public static void reset() {
        if (driver != null)
            driver.closeAll();
        driver = null;
    }
}
