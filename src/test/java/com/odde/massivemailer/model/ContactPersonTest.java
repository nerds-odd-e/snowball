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
	public void testCreateContactObjectWithoutCompany() {

		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		ContactPerson person = new ContactPerson(name, email, lastname);

		assertEquals(name, person.getName());
		assertEquals(email, person.getEmail());
		assertEquals(lastname, person.getLastname());
		assertEquals("", person.getCompany());
	}


	@Test
	public void testCreateContactObjectWithCompany() {

		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		String company = "myCompany";
		ContactPerson person = new ContactPerson(name, email, lastname, company);

		assertEquals(name, person.getName());
		assertEquals(email, person.getEmail());
		assertEquals(lastname, person.getLastname());
		assertEquals(company, person.getCompany());
	}


	@Test
	public void testCreateContactObjectWithLocation() {

		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		String company = "myCompany";
		String location = "Singapore/Singapore";
		ContactPerson person = new ContactPerson(name, email, lastname, company, location);

		assertEquals(name, person.getName());
		assertEquals(email, person.getEmail());
		assertEquals(lastname, person.getLastname());
		assertEquals(company, person.getCompany());
		assertEquals(location, person.getLocation());
	}

	@Test
	public void testCreateContactObjectValuesWithLocationAndCoOrdinates() {

		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		String company = "myCompany";
		String location = "Singapore/Singapore";
		ContactPerson person = new ContactPerson(name, email, lastname, company, location);

		assertEquals(name, person.getName());
		assertEquals(email, person.getEmail());
		assertEquals(lastname, person.getLastname());
		assertEquals(company, person.getCompany());
		assertEquals(location, person.getLocation());
		assertNotNull(person.getLatitude());
		assertNotNull(person.getLongitude());

	}


	@Test
	public void testCreateContactObjectValuesInDBWithLocationAndCoOrdinates() throws  Exception{
		String name = "name";
			String email = "email@abc.com";
			String lastname = "lastname";
			String company = "myCompany";
			String location = "Singapore/Singapore";
			ContactPerson person = new ContactPerson(name, email, lastname, company, location);
			ContactPerson personInDB =CreateContactInDB(person);
			assertNotNull(personInDB.getLatitude());
			assertNotNull(personInDB.getLongitude());
	}

	@Test
	public void testConsentReqAndReceivedIsSaved() throws Exception {
		String name = "name";
		String email = "email@abc.com";
		String lastname = "lastname";
		String company = "myCompany";
		String location = "Singapore/Singapore";
		ContactPerson person = new ContactPerson(name, email, lastname, company, location);
		LocalDate requestDate = LocalDate.of(2018, 6, 18);
		LocalDate receivedDate = LocalDate.of(2018, 6, 20);

		person.setConsentSend(requestDate);
		person.setConsentReceived(receivedDate);

		ContactPerson personInDB = CreateContactInDB(person);

		assertEquals(requestDate, personInDB.getConsentSend());
		assertEquals(receivedDate, personInDB.getConsentReceived());
	}

	private ContactPerson CreateContactInDB(ContactPerson person) throws Exception {

		person.saveIt();
		return person;
	}



	@Test
	public void testGetInvalidAttributeValue() throws Exception {
		ContactPerson contact =  new ContactPerson("John", "john@gmail.com", "Doe");
		thrown.expect(IllegalArgumentException.class);
		contact.getAttribute("Invalid");
	}


	@Test
	public void testGetContactsWithoutConsentRequest() {

		ContactPerson.getContactsWithoutConsentRequest().forEach((item) -> {
			assertNull(item.getConsentSend());
		});

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
	public void testDeleteUser() {
		ContactPerson person = new ContactPerson("testPerson", "bla@bla.com", "TestPersonLastName La");
		assertFalse(person.isForgotten());

		person.setForgotten(true);
		assertTrue(person.isForgotten());

		person.saveIt();

		ContactPerson fromDb = ContactPerson.findById(person.getId());
		assertTrue(fromDb.isForgotten());
	}

	@Test
	public void test_prepare_list_of_contacts_should_create_listof_contacts() {

		String csvData = preparecsvDataForTest();

		List<ContactPerson> contacts = ContactPerson.prepareContactsList(csvData);
		assertEquals(1, contacts.size());

	}

	@Test
	public void test_create_contacts_should_create_same_details_that_was_requested() {

		String csvData = preparecsvDataForTest();
		ContactPerson.createContacts(csvData);

		List<ContactPerson> contacts = ContactPerson.prepareContactsList(csvData);

		assertTrue(isEquals(contacts, "balakg@gmail.com"));
		assertFalse(isEquals(contacts, "forshailesh@gmail.com"));

	}

	private boolean isEquals(List<ContactPerson> newContacts, String email) {
		return newContacts.get(0).equals(ContactPerson.getContactByEmail(email));
	}

	private String preparecsvDataForTest() {
		return "email,firstname,lastname,company,country,city;Bala,balakg@gmail.com,GovindRaj,CS,Singapore,Singapore";
	}

}
