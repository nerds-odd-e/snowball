package com.odde.massivemailer.model;

import com.odde.TestWithDB;
import com.odde.massivemailer.util.NotificationUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.mail.Message;
import javax.mail.Session;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class MailTest {
	
	@Test @Ignore
	public void testCreateMessage() throws Exception {

		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setReceipts(Arrays.asList("test@gmail.com"));
		NotificationUtil.addNotification(mail);

		List<Message> messages = mail.createMessages(session);
		
		assertEquals(1, messages.size());
		assertEquals(mail.getSubject(), messages.get(0).getSubject());
	}

	@Test
	public void testCreateMessageWithTemplate() throws Exception {
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		ContactPerson contactPerson = new ContactPerson();
		contactPerson.setName("TestName");
		contactPerson.setLastname("LastName");
		contactPerson.setEmail("test@gmail.com");
		contactPerson.setCompany("CompanyName");
		contactPerson.saveIt();

		Mail mail = new Mail();
		mail.setMessageId(System.currentTimeMillis());
		mail.setContent("content {FirstName}, {Company}");
		mail.setSubject("subject {LastName} - {Email}");
		mail.setReceipts(Arrays.asList("test@gmail.com"));

		NotificationUtil.addNotification(mail);

		List<Message> messages = mail.createMessages(session);

		assertEquals("<html><body>content TestName, CompanyName<img height=\"42\" width=\"42\" src=\"http://"+ InetAddress.getLocalHost().getHostAddress()+":8070/massive_mailer/resources/images/qrcode.png?token=" + mail.getNotification().getNotificationDetails().get(0).getId() + "\"></img></body></html>", messages.get(0).getContent());
		assertEquals("subject LastName - test@gmail.com", messages.get(0).getSubject());
	}


	@Test
	public void testDisplayName() throws Exception {

		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		
		Mail mail = new Mail();
		mail.setContent("content");
		mail.setSubject("subject");
		mail.setReceipts(Arrays.asList("test@gmail.com"));
		NotificationUtil.addNotification(mail);
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

		Notification notification = mail.asNotification();

		assertThat(notification.getSubject(), is("subject"));
		assertThat(notification.getNotificationId(), is(123456789L));
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

        Notification notification = mail.asNotification();

        List<NotificationDetail> notificationDetails = notification.getNotificationDetails();

        assertThat(notification.getNotificationDetails().size(), is(1));
        assertThat(notification.getNotificationDetails().get(0).getEmailAddress(), is("terry@odd-e.com"));
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

        Notification notification = mail.asNotification();

        assertThat(notification.getNotificationDetails().size(), is(3));
        assertThat(notification.getNotificationDetails().get(0).getEmailAddress(), is("terry@odd-e.com"));
        assertThat(notification.getNotificationDetails().get(1).getEmailAddress(), is("terry2@odd-e.com"));
        assertThat(notification.getNotificationDetails().get(2).getEmailAddress(), is("terry3@odd-e.com"));
    }
}
