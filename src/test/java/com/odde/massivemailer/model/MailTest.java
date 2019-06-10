package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.MockMailService;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.mail.Message;
import javax.mail.Session;
import java.net.InetAddress;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class MailTest {
	
	@Test
	public void testCreateMessage() throws Exception {

		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setReceipts(Collections.singletonList("test@gmail.com"));
		mail.asSentMail();

		List<Message> messages = mail.createMessages(session);
		
		assertEquals(1, messages.size());
		assertEquals(mail.getSubject(), messages.get(0).getSubject());
	}

	@Test
	public void testCreateMessageWithTemplate() throws Exception {
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		ContactPerson contactPerson = new ContactPerson();
		contactPerson.setFirstName("TestName");
		contactPerson.setLastName("LastName");
		contactPerson.setEmail("test@gmail.com");
		contactPerson.setCompany("CompanyName");
		contactPerson.save();

		Mail mail = new Mail();
		mail.setMessageId(System.currentTimeMillis());
		mail.setContent("content {FirstName}, {Company}");
		mail.setSubject("subject {LastName} - {Email}");
		mail.setReceipts(Collections.singletonList("test@gmail.com"));

		mail.asSentMail();

		List<Message> messages = mail.createMessages(session);

		assertEquals("<html><body>content TestName, CompanyName<img height=\"42\" width=\"42\" src=\"http://"+ InetAddress.getLocalHost().getHostAddress()+":8070/resources/images/qrcode.png?token=" + mail.getSentMail().getSentMailVisits().get(0).getId() + "\"></img></body></html>", messages.get(0).getContent());
		assertEquals("subject LastName - test@gmail.com", messages.get(0).getSubject());
	}


	@Test
	public void testDisplayName() throws Exception {

		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setReceipts(Collections.singletonList("test@gmail.com"));
		mail.asSentMail();
		List<Message> messages = mail.createMessages(session);
		String[] address = messages.get(0).getFrom()[0].toString().split("<");
		
		assertEquals("Inspector Gadget", address[0].trim());
	}

	@Test
	public void AsNotification() {
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setMessageId(123456789L);

		SentMail sentMail = mail.asSentMail();

		assertThat(sentMail.getSubject(), is("subject"));
		assertThat(sentMail.getMessageId(), is(123456789L));
	}

	@Test
	public void AsNotificationWithOneReceipt() {
        Mail mail = new Mail();
        mail.setContent("content");
        mail.setSubject("subject");
        mail.setMessageId(123456789L);

        List<String> receipts = new ArrayList<>();
        receipts.add("terry@odd-e.com");
        mail.setReceipts(receipts);

        SentMail sentMail = mail.asSentMail();

		assertThat(sentMail.getSentMailVisits().size(), is(1));
        assertThat(sentMail.getSentMailVisits().get(0).getEmailAddress(), is("terry@odd-e.com"));
    }

    @Test
    public void AsNotificationWithMultipleReceipts() {
        Mail mail = new Mail();
        mail.setContent("content");
        mail.setSubject("subject");
        mail.setMessageId(123456789L);

        List<String> receipts = new ArrayList<>();
        receipts.add("terry@odd-e.com");
        receipts.add("terry2@odd-e.com");
        receipts.add("terry3@odd-e.com");
        mail.setReceipts(receipts);

        SentMail sentMail = mail.asSentMail();

        assertThat(sentMail.getSentMailVisits().size(), is(3));
        assertThat(sentMail.getSentMailVisits().get(0).getEmailAddress(), is("terry@odd-e.com"));
        assertThat(sentMail.getSentMailVisits().get(1).getEmailAddress(), is("terry2@odd-e.com"));
        assertThat(sentMail.getSentMailVisits().get(2).getEmailAddress(), is("terry3@odd-e.com"));
    }

    @Test
    public void testSendWithFunction() throws EmailException {
        Mail m = new Mail(1,"Subject","Content");
        ArrayList<String> receipts = new ArrayList<>();
        receipts.add("receipt");
        m.setReceipts(receipts);
        MailService service = new MockMailService();
        SentMail sentmail= m.sendMailWith(service);
		assertNotNull(sentmail.getId());
    }

}



