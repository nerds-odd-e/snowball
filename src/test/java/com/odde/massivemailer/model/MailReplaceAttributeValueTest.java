package com.odde.massivemailer.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MailReplaceAttributeValueTest {

	private ContactPerson contact = new ContactPerson("John", "john@gmail.com", "Doe", "Edd-o");
	private Mail mail = new Mail();

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "Greeting {{{FirstName}}} {{FirstName}} John FirstName", "Greeting {{{FirstName}}} {{FirstName}} {FirstName} FirstName" },
				{ "Greeting {{{LastName}}} {{LastName}} Doe LastName", "Greeting {{{LastName}}} {{LastName}} {LastName} LastName" },
				{ "Greeting {{{Email}}} {{Email}} john@gmail.com Email", "Greeting {{{Email}}} {{Email}} {Email} Email" },
				{ "Greeting {{{Company}}} {{Company}} Edd-o Company", "Greeting {{{Company}}} {{Company}} {Company} Company" }
		});
	}

	@Parameterized.Parameter
	public String expectedResult;

	@Parameterized.Parameter (value = 1)
	public String template;


	@Test
	public void testReplaceAttributesValue(){

		assertEquals(expectedResult, mail.ReplaceAttibute(template, contact));

	}

}
