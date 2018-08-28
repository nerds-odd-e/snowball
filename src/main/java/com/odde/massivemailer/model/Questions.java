package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

public class Questions extends Model{
	private String description;
	private int isMultiQuestion;
	private String advice;

	public String getDescription() {
		return this.description;
	}

	public int getIsMultiQuestion() {
		return this.isMultiQuestion;
	}

	public String getAdvice() {
		return this.advice;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setIsMultiQuestion(int isMultiQuestion){
		this.isMultiQuestion = isMultiQuestion;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
}
