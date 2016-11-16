package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Mail;

import java.util.List;

public interface EmailService {

	List<Mail> getSentEmailList();
 	void setSentEmailList(List<Mail> emailList);

	List<Mail> getOpenedEmailCountList();



	void destroyAll();

    void setOpenedEmailCountList(int i);

	String getEmailCounterJson(int email_id);
}