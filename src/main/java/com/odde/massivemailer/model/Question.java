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
	public void setIsMultiQuestion(int isMultiQuestion){
		set("is_multi_question", isMultiQuestion);
	}

	public void setAdvice(String advice) {
		set("advice", advice);
	}
}
