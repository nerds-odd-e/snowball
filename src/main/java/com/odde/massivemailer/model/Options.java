package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;

public class Options extends Model{
	private int questionId;
	private String option;
	private int isCorrect;
	public int getQuestionId(){
		return this.questionId;
	}
	public String getOption(){
		return this.option;
	}
	public int getIsCorrect(){
		return this.isCorrect;
	}
	public void setQuestionId(int questionId){
		this.questionId = questionId;
	}
	public void setOption(String option){
		this.option = option;
		set("description", option);
	}
	public void setIsCorrect(int isCorrect){
		this.isCorrect = isCorrect;
	}

}
