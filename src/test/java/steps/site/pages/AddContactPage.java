package steps.site.pages;

import steps.driver.WebDriverWrapper;
import steps.site.MassiveMailerSite;

public class AddContactPage {
    private final MassiveMailerSite site;
    private final WebDriverWrapper driver;

    public AddContactPage(MassiveMailerSite massiveMailerSite) {
        site = massiveMailerSite;
        this.driver = site.getDriver();
        site.visit("add_contact.jsp");
    }

    public void addContactWithLocationString(String email, String location) {
        String[] location_parts = location.split("/");
        String country = location_parts[0];
        String city = location_parts[1];
        addContact(email, country, city);
    }

    public void addContact(String email, String country, String city) {
        driver.setTextField("email", email);
        driver.setDropdownValue("country", country);
        driver.setTextField("city", city);
        driver.clickButton("add_button");
    }

    public void addContactWithAllInput(String email, String country, String city, String name, String lastName, String company) {
        driver.setTextField("name", name);
        driver.setTextField("lastName", lastName);
        driver.setTextField("company", company);
        this.addContact(email, country, city);
    }

}
