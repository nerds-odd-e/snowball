package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

public class Options extends Model{
	private int questionId;
	private String description;
	private int isCorrect;
	public int getQuestionId(){
		return this.questionId;
	}
	public String getDescription(){
		return this.description;
	}
	public int getIsCorrect(){
		return this.isCorrect;
	}
	public void setQuestionId(int questionId){
		this.questionId = questionId;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setIsCorrect(int isCorrect){
		this.isCorrect = isCorrect;
	}

}
