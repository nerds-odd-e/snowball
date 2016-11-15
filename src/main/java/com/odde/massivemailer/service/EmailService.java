package com.odde.massivemailer.service;

import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Mail;

import java.sql.SQLException;
import java.util.List;

public interface EmailService {

	List<Mail> getSentEmailList();
 	void setSentEmailList(List<Mail> emailList);

	void destroyAll();
}