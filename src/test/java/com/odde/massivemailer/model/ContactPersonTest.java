package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class ContactPersonTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCreateContactObjectWithoutCompany() {
        ContactPerson.createIt(
                "firstname", "name",
                "email", "email@abc.com",
                "lastname", "lastname");

        ContactPerson actual = ContactPerson.getContactByEmail("email@abc.com");

        assertEquals("name", actual.getName());
        assertEquals("email@abc.com", actual.getEmail());
        assertEquals("lastname", actual.getLastname());
        assertEquals("", actual.getCompany());
    }


    @Test
    public void testCreateContactObjectValuesWithLocationAndCoOrdinates() {

        Map<String, String> map = new HashMap<>();
        map.put("firstname", "name");
        map.put("email", "email@abc.com");
        map.put("lastname", "lastname");
        map.put("company", "myCompany");
        map.put("city", "Singapore");
        map.put("country", "Singapore");
        new ContactPerson().fromMap(map).saveIt();
        ContactPerson actual = ContactPerson.getContactByEmail("email@abc.com");

        assertEquals("name", actual.getName());
        assertEquals("lastname", actual.getLastname());
        assertEquals("myCompany", actual.getCompany());
        assertEquals("Singapore", actual.getString("city"));
        assertNotNull(actual.getLatitude());
        assertNotNull(actual.getLongitude());

    }

    @Test
    public void UpdateContactWhenEmailSent() {
        ContactPerson p = ContactPerson.create("email", "john@gmail.com");
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
        ContactPerson.createContactsFromCSVData(csvData);

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
        ContactPerson.createContactsFromCSVData(csvData);

        return ContactPerson.prepareContactsList(csvData);
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
