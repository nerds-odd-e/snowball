package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionController extends AppController {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		new Question()
				.set("description", req.getParameter("description"))
				.set("is_multi_question", 0)
				.set("advice", "")
				.saveIt();
	}


}
