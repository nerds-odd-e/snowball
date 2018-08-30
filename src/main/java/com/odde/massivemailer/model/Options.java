package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


@Table("options")
public class Options extends ApplicationModel{
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

	public String getDescription() {
		return (String) get("description");
	}

	public void setIsCorrect(int isCorrect){
		this.isCorrect = isCorrect;
	}

	public static Options getById(int id) {
		return (Options) findAll().get(id-1);
	}
}
