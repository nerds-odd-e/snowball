package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;

public interface MailService {
	void send(Mail email) throws EmailException;
}
