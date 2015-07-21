package com.odde.massivemailer.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.odde.massivemailer.model.Mail;

public class SendMailController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Mail email = processRequest(req);

	}

	public Mail processRequest(HttpServletRequest req) {

		Mail email = new Mail();
		String tempRecipient = req.getParameter("recipient");
		StringTokenizer st = new StringTokenizer(tempRecipient, ";");
		ArrayList<String> recipientList = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			recipientList.add(st.nextToken());
		}

		email.setContent(req.getParameter("content"));
		email.setSubject(req.getParameter("subject"));
		email.setReceipts(recipientList);

		return email;
	}

}
