package com.odde.massivemailer.model;

import com.odde.massivemailer.model.validator.UniquenessValidator;
import com.odde.massivemailer.service.LocationProviderService;
import com.odde.massivemailer.service.exception.GeoServiceException;
import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.annotations.Table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Table("contact_people")
public class ContactPerson extends ApplicationModel {
    public static final String FIRSTNAME = "FirstName";
    public static final String LASTNAME = "LastName";
    public static final String EMAIL = "Email";
    public static final String COMPANY = "Company";
    public static final String LOCATION = "Location";
    public static final String LONGITUDE = "Longitude";
    public static final String LATITUDE = "Latitude";
    public static final String CONSENT_SENT = "consent_sent";
    public static final String CONSENT_RECEIVED = "consent_received";
    public static final String FORGOTTEN = "forgotten";
    public static final String CONSENT_ID = "consent_id";
    private static final int EMAIL_INDEX = 0;
    private static final int FIRSTNAME_INDEX = 1;
    private static final int LASTNAME_INDEX = 2;
    private static final int COMPANY_INDEX = 3;
    private static final int COUNTRY_INDEX = 4;
    private static final int CITY_INDEX = 5;

    static {
        validatePresenceOf("email");
        validateWith(new UniquenessValidator("email"));
    }

    public Map<String, String> attributes = new HashMap<>();

    public ContactPerson() {
    }

    public ContactPerson(String email, String consentId) {
        setEmail(email);
        setConsentId(consentId);
    }

    public ContactPerson(String name, String email, String lastname) {
        this(name, email, lastname, "");
    }

    public ContactPerson(String name, String email, String lastname, String company) {
        setName(name);
        setEmail(email);
        setLastname(lastname);
        setCompany(company);
    }

    public ContactPerson(String name, String email, String lastname, String company, String location) {
        setName(name);
        setEmail(email);
        setLastname(lastname);
        setCompany(company);
        setLocation(location);
        if (!StringUtils.isEmpty(getLocation())) {
            LocationProviderService locationProviderService = new LocationProviderService();
            try {
                Location locationDetails = locationProviderService.getLocationForName(getLocation());

                if (locationDetails != null) {
                    set(LATITUDE, locationDetails.getLat());
                    set(LONGITUDE, locationDetails.getLng());
                }
            } catch (GeoServiceException e) {
                throw new RuntimeException("failed to get location.", e);
            }
        }
    }

    public ContactPerson(String name, String email, String lastname, String company, String location, String coursesList, String dateSent) {
        setName(name);
        setEmail(email);
        setLastname(lastname);
        setCompany(company);
        setLocation(location);
        setCourseList(coursesList);
        setSentDate(dateSent);
    }

    public static List<ContactPerson> whereHasLocation() {
        return where(LOCATION + "<>''");
    }

    public static boolean createContact(String city, String country, String email, String name, String lastname, String company, String consent_id) {
        String location = country + "/" + city;
        new LocationProviderService().cacheLocation(city, country, location);

        ContactPerson contact = new ContactPerson(name, email, lastname, company, location);
        contact.setConsentId(consent_id);
        return contact.saveIt();
    }


    public static void createContacts(String csvData) {

        List<ContactPerson> contacts = prepareContactsList(csvData);

        for (int i = 0; i < contacts.size(); i++) {
            ContactPerson contact;
            contact = contacts.get(i);
            contact.saveIt();
        }
    }

    public static List<ContactPerson> prepareContactsList(String csvData) {

        String[] contactPersonList = csvData.split(";");
        List<ContactPerson> contacts = new ArrayList<>();

        for (int i = 1; i < contactPersonList.length; i++) {
            String currentContact = contactPersonList[i];
            String[] contactInformation = currentContact.split(",");

            ContactPerson contactPerson = new ContactPerson(
                    contactInformation[FIRSTNAME_INDEX], contactInformation[EMAIL_INDEX], contactInformation[LASTNAME_INDEX],
                    contactInformation[COMPANY_INDEX],
                    contactInformation[CITY_INDEX] + "/" + contactInformation[COUNTRY_INDEX]);
            contacts.add(contactPerson);
        }

        return contacts;
    }

    public static List<ContactPerson> getContactListFromCompany(String company) {
        return where("company = ?", company);
    }

    public static ContactPerson getContactByEmail(String emailAddress) {
        LazyList<ContactPerson> list = where("email = ?", emailAddress);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    public static ContactPerson getContactById(Integer contactId) {
        LazyList<ContactPerson> list = where("id = ?", contactId.intValue());
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    static boolean isValidEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    static boolean isValidCountry(String country) {
        return validCountryList().contains(country.toLowerCase());
    }

    private static List<String> validCountryList() {
        String csvFile = "src/main/resources/csv/countries.csv";
        String line = "";
        List<String> validCountries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                validCountries.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return validCountries;
    }

    public Double getLatitude() {
        return getDoubleAttribute(LATITUDE);
    }

    public Double getLongitude() {
        return getDoubleAttribute(LONGITUDE);
    }

    public String getName() {
        return getAttribute(FIRSTNAME);
    }

    public void setName(String name) {
        setAttribute(FIRSTNAME, name);
    }

    public String getEmail() {
        return getAttribute(EMAIL);
    }

    public void setEmail(String email) {
        setAttribute(EMAIL, email);
    }

    public String getLastname() {
        return getAttribute(LASTNAME);
    }

    public void setLastname(String lastname) {
        setAttribute(LASTNAME, lastname);
    }

    public String getCompany() {
        return getAttribute(COMPANY);
    }

    public void setCompany(String company) {
        setAttribute(COMPANY, company);
    }

    private void setAttribute(String name, String value) {
        attributes.put(name, value);
        set(name.toLowerCase(), value);
    }

    public double getDoubleAttribute(String name) {
        return (double) get(name);
    }

    public String getConsentId() {
        return getAttribute(CONSENT_ID);
    }

    public void setConsentId(String consentId) {
        setAttribute(CONSENT_ID, consentId);
    }

    public String getAttribute(String name) {
        Object o = get(name);
        if (o != null)
            return o.toString();
        return "";
    }

    public Set<String> getAttributeKeys() {
        return getMetaModel().getAttributeNamesSkipId();
    }

    public String getLocation() {
        return getAttribute(LOCATION);
    }

    public void setLocation(String location) {
        setAttribute(LOCATION, location);
    }

    public Location getGeoCoordinates() {
        return new Location(getLocation(), getDoubleAttribute(LATITUDE), getDoubleAttribute(LONGITUDE));
    }

    public boolean AddToCourse(String courseId) {
        int participantId = (int) getId();

        Participant contactParticipant = new Participant(new Integer(participantId), new Integer(courseId));

        return contactParticipant.save();
    }

    public String errorMessage() {
        return "Unable to register participants";
    }

    public void setCourseList(String coursesList) {
        set("courses_sent", coursesList);
    }

    public Object getCoursesList() {
        return get("courses_sent");
    }

    public Object getSentDate() {
        return get("date_sent");
    }

    public void setSentDate(String sentDate) {
        set("date_sent", sentDate);
    }

    @Override
    public boolean equals(Object o) {
        ContactPerson cp = (ContactPerson) o;
        return o != null && Objects.equals(getName(), cp.getName())
                && Objects.equals(getEmail(), cp.getEmail())
                && Objects.equals(getLastname(), cp.getLastname())
                && Objects.equals(getCompany(), cp.getCompany())
                && Objects.equals(getLocation(), cp.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributes());
    }
}
