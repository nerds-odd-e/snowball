package cucumber.steps.site.pages;

import cucumber.steps.driver.WebDriverWrapper;
import cucumber.steps.site.SnowballSite;

public class CourseListPage {
    private final WebDriverWrapper driver;
    private final SnowballSite site;

    public CourseListPage(SnowballSite site) {

        this.site = site;
        this.driver = site.driver;
    }

    public void sendPreviewEmailFor(String a_course) {
        site.visit("course_list.jsp");
        driver.clickButtonByName("preview " + a_course);
    }

    public void sendPrecourseEmailFor(String aCourse) {
        site.visit("course_list.jsp");
        driver.clickButtonByName("precourse " + aCourse);
    }
}
