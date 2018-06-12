package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class CourseListPage {
    private WebDriverWrapper driver;
    private MassiveMailerSite site;

    public CourseListPage(MassiveMailerSite site) {

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
