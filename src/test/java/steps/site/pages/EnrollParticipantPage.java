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

    public void addStudentTo(String a_course, String email) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        site.visit("enrollParticipant.jsp");
        driver.setDropdownByText("courseId", a_course);
        driver.setTextField("email", email);
        driver.clickButton("add_button");
    }
}
