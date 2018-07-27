package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;


import java.time.LocalDate;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class ContactPersonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_createContactWithConsentId() {
        String email = "email@abc.com";
        String consentId = "1234";
        ContactPerson contactPerson = new ContactPerson(email, consentId);

        contactPerson.saveIt();

        ContactPerson actual = ContactPerson.getContactByEmail(email);

        assertEquals("email@abc.com", actual.getEmail());
        assertEquals("1234", actual.getConsentId());
    }

    @Test
    public void testCreateContactObjectWithoutCompany() {

        String name = "name";
        String email = "email@abc.com";
        String lastname = "lastname";
        ContactPerson person = new ContactPerson(name, email, lastname);

        person.saveIt();

        ContactPerson actual = ContactPerson.getContactByEmail(email);

        assertEquals(name, actual.getName());
        assertEquals(email, actual.getEmail());
        assertEquals(lastname, actual.getLastname());
        assertEquals("", actual.getCompany());
    }


    @Test
    public void testCreateContactObjectWithCompany() {

        String name = "name";
        String email = "email@abc.com";
        String lastname = "lastname";
        String company = "myCompany";
        ContactPerson person = new ContactPerson(name, email, lastname, company);

        person.saveIt();

        ContactPerson actual = ContactPerson.getContactByEmail(email);

        assertEquals(name, actual.getName());
        assertEquals(email, actual.getEmail());
        assertEquals(lastname, actual.getLastname());
        assertEquals(company, actual.getCompany());
    }


    @Test
    public void testCreateContactObjectWithLocation() {

        String name = "name";
        String email = "email@abc.com";
        String lastname = "lastname";
        String company = "myCompany";
        String location = "Singapore/Singapore";
        ContactPerson person = new ContactPerson(name, email, lastname, company, location);

        person.saveIt();

        ContactPerson actual = ContactPerson.getContactByEmail(email);

        assertEquals(name, actual.getName());
        assertEquals(email, actual.getEmail());
        assertEquals(lastname, actual.getLastname());
        assertEquals(company, actual.getCompany());
        assertEquals(location, actual.getLocation());
    }

    @Test
    public void testCreateContactObjectValuesWithLocationAndCoOrdinates() {

        String name = "name";
        String email = "email@abc.com";
        String lastname = "lastname";
        String company = "myCompany";
        String location = "Singapore/Singapore";
        ContactPerson person = new ContactPerson(name, email, lastname, company, location);

        person.saveIt();
        ContactPerson actual = ContactPerson.getContactByEmail(email);

        assertEquals(name, actual.getName());
        assertEquals(email, actual.getEmail());
        assertEquals(lastname, actual.getLastname());
        assertEquals(company, actual.getCompany());
        assertEquals(location, actual.getLocation());
        assertNotNull(actual.getLatitude());
        assertNotNull(actual.getLongitude());

    }


    @Test
    public void testCreateContactObjectValuesInDBWithLocationAndCoOrdinates() throws Exception {
        String name = "name";
        String email = "email@abc.com";
        String lastname = "lastname";
        String company = "myCompany";
        String location = "Singapore/Singapore";
        ContactPerson person = new ContactPerson(name, email, lastname, company, location);
        ContactPerson personInDB = CreateContactInDB(person);
        assertNotNull(personInDB.getLatitude());
        assertNotNull(personInDB.getLongitude());
    }

    private ContactPerson CreateContactInDB(ContactPerson person) throws Exception {

        person.saveIt();
        return person;
    }


    @Test
    public void testGetInvalidAttributeValue() throws Exception {
        ContactPerson contact = new ContactPerson("John", "john@gmail.com", "Doe");
        thrown.expect(IllegalArgumentException.class);
        contact.getAttribute("Invalid");
    }


    @Test
    public void UpdateContactWhenEmailSent() {

        assertEquals(0, ContactPerson.findAll().size());
        ContactPerson p = new ContactPerson("John", "john@gmail.com", "Doe");
        p.setCourseList("1,2,3");
        p.setSentDate("2017-11-30");
        p.saveIt();

        ContactPerson actual = ContactPerson.findById(p.getId());
        assertEquals("1,2,3", actual.getCoursesList());
        assertEquals("2017-11-30", actual.getSentDate().toString());
    }

    @Test
    public void test_prepare_list_of_contacts_should_create_listof_contacts() {

        String csvData = preparecsvDataForTest();

        List<ContactPerson> contacts = ContactPerson.prepareContactsList(csvData);
        assertEquals(2, contacts.size());
    }

    @Test
    public void test_create_contacts_should_create_same_details_that_was_requested() {

        String csvData = preparecsvDataForTest();
        ContactPerson.createContacts(csvData);

        List<ContactPerson> contacts = ContactPerson.prepareContactsList(csvData);

        //assertEquals(contacts.get(0).getEmail(), "balakg@gmail.com");
        assertTrue(isEquals(contacts, "balakg@gmail.com"));
        assertFalse(isEquals(contacts, "forshailesh@gmail.com"));

    }

    private String preparecsvDataForTest() {
        return "email,firstname,lastname,company,country,city;balakg@gmail.com,Bala,GovindRaj,CS,Singapore,Singapore;forshailesh@gmail.com,Shailesh,Thakur,CS,Singapore,Singapore";
    }

    private boolean isEquals(List<ContactPerson> newContacts, String email) {
        return newContacts.get(0).equals(ContactPerson.getContactByEmail(email));
    }

    @Test
    public void willReturnTrueIfEmailIsValid() {
        assertTrue(ContactPerson.isValidEmail("abc@email.com"));
    }

    @Test
    public void willReturnFalseIfEmailIsInvalid() {
        assertFalse(ContactPerson.isValidEmail("abc"));
    }

    @Test
    public void willReturnTrueIfCountryIsValid() {
        assertTrue(ContactPerson.isValidCountry("singapore"));
    }

    @Test
    public void willReturnFalseIfCountryIsInvalid() {
        assertFalse(ContactPerson.isValidCountry("Mingjia"));
    }

    private List<ContactPerson> createContactsForTest() {
        String csvData = preparecsvDataForTest();
        ContactPerson.createContacts(csvData);

        return ContactPerson.prepareContactsList(csvData);
    }

    private List<ContactPerson> getContactPeopleList(String name, String email, String lastname, String company, String location) {
        List<ContactPerson> newContacts = new ArrayList();
        ContactPerson contacts = new ContactPerson(name, email, lastname, company, location);
        newContacts.add(contacts);
        return newContacts;
    }

    @Test
    public void noAdditionalContactAddedWhenContactAlreadyExist() {
        List<ContactPerson> contacts = createContactsForTest();
        assertEquals(2, contacts.size());

        int shaileshCount = 0;
        for (ContactPerson cp : contacts) {
            if (cp.getEmail().equals("forshailesh@gmail.com")) {
                shaileshCount++;
            }
        }
        assertEquals(1, shaileshCount);

    }


}
