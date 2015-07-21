package com.odde.massivemailer.model;

import java.util.List;

public class Mail {
	List<String> receipts;
	String subject;
	String content;
	
	public List<String> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<String> receipts) {
		this.receipts = receipts;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
