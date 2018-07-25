package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class EnrollParticipantPage {
    private final MassiveMailerSite site;
    private WebDriverWrapper driver;

    public EnrollParticipantPage(MassiveMailerSite site) {
        this.site = site;
        this.driver = site.driver;
    }

    public void addStudentsTo(String a_course, String information) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        site.visit("enrollParticipant.jsp");
        driver.setDropdownByText("courseId", a_course);
        driver.setTextField("participants", information);
        driver.clickButton("add_button");
    }
}
