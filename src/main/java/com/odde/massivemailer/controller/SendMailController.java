package com.odde.massivemailer.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.odde.massivemailer.startup.MassiveMailerServletContextListener;

public class SendMailController extends HttpServlet {

	private SqliteContact sqliteContact;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Mail email = processRequest(req);
			MailService mailService = new GMailService();
			mailService.setConfiguration(new SMTPConfiguration(MassiveMailerServletContextListener.EMAIL_USERID, MassiveMailerServletContextListener.EMAIL_PASSWORD, "smtp.gmail.com", 587));
			mailService.send(email);
			resp.sendRedirect("sendemail.jsp?status=success&msg=Email successfully sent&repcnt="+email.getReceipts().size());
		} catch (EmailException e) {
			resp.sendRedirect("sendemail.jsp?status=failed&msg=Unable to send");
			e.printStackTrace();
			//
		} catch (SQLException e) {
			resp.sendRedirect("sendemail.jsp?status=failed&msg=Fail");
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

				if(company.startsWith("\"") && company.endsWith("\"") && countCharOccurrence(company, '"') == 2){ 
					company = company.substring(1, company.length()-1); 				
				} else if (company.startsWith("\"") && countCharOccurrence(company, '"') == 1) {
					company = company.substring(1, company.length());
				}
				SqliteContact contactService = getSqliteContact();
				contactList = contactService.getContactListFromCompany(company);
				if (contactList.isEmpty()) {
					throw new SQLException() ;
				}
				for (ContactPerson contactPerson : contactList) {
					recipientList.add(contactPerson.getEmail());
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

	public SqliteContact getSqliteContact() {
		
		return (sqliteContact == null) ? sqliteContact = new SqliteContact() : sqliteContact;
	}
	
	public void setSqliteContact(SqliteContact contactService)
	{
		sqliteContact = contactService;
	}
	
	
	private int countCharOccurrence(String input, char countedChar)
	{
		int counter = 0;
		for( int i=0; i < input.length(); i++ ) {
		    if( input.charAt(i) == countedChar ) {
		        counter++;
		    } 
		}
		return counter;
	}

}
