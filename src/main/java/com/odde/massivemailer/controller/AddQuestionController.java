package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Options;
import com.odde.massivemailer.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionController extends AppController {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		new Question()
				.set("description", req.getParameter("description"))
				.set("is_multi_question", 0)
				.set("advice", req.getParameter("advice"))
				.saveIt();
		new Options()
				.set("description", req.getParameter("option1"))
				.set("question_id", 0)
				.set("is_correct", 0)
				.saveIt();
		new Options()
				.set("description", req.getParameter("option2"))
				.set("question_id", 0)
				.set("is_correct", 0)
				.saveIt();
	}


}
