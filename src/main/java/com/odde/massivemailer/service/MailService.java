package com.odde.massivemailer.service;

import com.odde.massivemailer.exception.EmailException;
import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.service.impl.SMTPConfiguration;

public interface MailService {
	void send(Mail email) throws EmailException;

	void setConfiguration(SMTPConfiguration config);
}
