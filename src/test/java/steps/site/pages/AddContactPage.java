package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class AddContactPage {
    private MassiveMailerSite site;
    private WebDriverWrapper driver;

    public AddContactPage(MassiveMailerSite massiveMailerSite) {
        site = massiveMailerSite;
        this.driver = site.getDriver();
    }

    public void addContactWithLocationString(String email, String location) throws Throwable{
        String[] location_parts = location.split("/");
        String country = location_parts[0];
        String city = location_parts[1];
        addContact(email, country, city);
    }

    public void addContact(String email, String country, String city) throws Throwable{
        site.visit("add_contact.jsp");
        driver.setTextField("email", email);
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
        driver.clickButton("add_button");
    }
}
