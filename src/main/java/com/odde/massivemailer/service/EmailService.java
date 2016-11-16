package com.odde.massivemailer.service;

import com.odde.massivemailer.model.Mail;
import com.odde.massivemailer.model.Notification;

import java.util.List;

public interface EmailService {

	List<Notification> getSentEmailList();
 	void setSentEmailList(List<Notification> emailList);

	List<Notification> getOpenedEmailCountList();



	void destroyAll();

    void setOpenedEmailCountList(int i);

	String getEmailCounterJson(int email_id);
}