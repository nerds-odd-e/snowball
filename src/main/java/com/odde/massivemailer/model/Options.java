package com.odde.massivemailer.model;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;


@Table("options")
public class Options extends ApplicationModel{
	private String option;

	public String getOption(){
		return this.option;
	}

	public void setOption(String option){
		this.option = option;
		set("description", option);
	}

	public String getDescription() {
		return (String) get("description");
	}

	public static Options getById(int id) {
		return (Options) findAll().get(id-1);
	}
}
