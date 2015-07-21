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
		Mail email = new Mail();
		String tempReceipt = req.getParameter("receipt");	
		StringTokenizer st = new StringTokenizer(tempReceipt,";");
		ArrayList<String> receiptList = new ArrayList();
		while(st.hasMoreTokens()){
			receiptList.add(st.nextToken());
		}
		
		email.setContent(req.getParameter("content"));
		email.setContent(req.getParameter("subject"));
		email.setReceipts(receiptList);
		
		//Test
		resp.sendRedirect("/index.jsp");
		
	}
	

}
