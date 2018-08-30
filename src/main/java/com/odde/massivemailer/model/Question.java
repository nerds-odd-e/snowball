package com.odde.massivemailer.model;

import org.javalite.activejdbc.annotations.Table;

@Table("questions")
public class Question extends ApplicationModel{

	public static Question getLast() {
		return (Question) findAll().get((int) (count() - 1));
	}

	public String getDescription() {
		return (String) get("description");
	}

	public void setDescription(String description){
		set("description", description);
	}

	public void setAdvice(String advice) {
		set("advice", advice);
	}
	public String getAdvice() {return (String) get("advice"); }

	public int getQuestionId() {return (Integer) get("id"); }
}
