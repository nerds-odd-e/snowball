package com.odde.massivemailer.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.MailService;
import com.odde.massivemailer.service.impl.GMailService;
import com.odde.massivemailer.service.impl.SMTPConfiguration;

import com.odde.massivemailer.service.impl.SqliteContact;

public class SendMailController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	

		//Mail email = processRequest(req);
		//MailService mailService = new GMailService();
		//mailService.setConfiguration(new SMTPConfiguration("myodde@gmail.com", "1234qwer@", "smtp.gmail.com", 587));

		try {
			Mail email = processRequest(req);
			MailService mailService = new GMailService();
			mailService.setConfiguration(new SMTPConfiguration("myodde@gmail.com", "1234qwer@", "smtp.gmail.com", 587));
			mailService.send(email);
			resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent&repcnt="+email.getReceipts().size());
		} catch (EmailException e) {
			resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
			e.printStackTrace();
			//
		} catch (SQLException e) {
			resp.sendRedirect("sendemail.jsp?status=failed&msg=something");
			e.printStackTrace();
		}
	}

	public Mail processRequest(HttpServletRequest req) throws SQLException {

		Mail email = new Mail();
		String tempRecipient = req.getParameter("recipient");
		StringTokenizer st = new StringTokenizer(tempRecipient, ";");
		ArrayList<String> recipientList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String recipient = st.nextToken();
			if (recipient.startsWith("company:")) {

				String[] aaa = recipient.split(":");
				List<ContactPerson> contactList;				
				String company = aaa[1].toString();
				
				 
//				"abc" -> abc
//				"abc  -> abc
//				a"bc  -> a"bc
				
// error cases				
//              "a"bc" --> a"bc
//				"a"bc -> "a"bc

				if(company.startsWith("\"") && company.endsWith("\"")){
					company = company.substring(1, company.length()-2);
				}
				
				SqliteContact contactService = new SqliteContact();
				contactList = contactService.getContactListFromCompany(company);

				if (contactList.isEmpty()) {
					throw new SQLException() ;
				}
			}
			else{
				recipientList.add(recipient);
			}
			
			
		}

		email.setContent(req.getParameter("content"));
		email.setSubject(req.getParameter("subject"));
		email.setReceipts(recipientList);

		return email;
	}

}
