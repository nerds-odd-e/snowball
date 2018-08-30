package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Options;
import com.odde.massivemailer.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionController extends AppController {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		Question question = new Question();
				question.set("description", req.getParameter("description"))
				.set("is_multi_question", 0)
				.set("advice", req.getParameter("advice"))
				.saveIt();

		for (int i = 1; i < 3; i++) {
			int is_correct = 0;
			if(i == 2){
				is_correct = 1;
			}
			new Options()
					.set("description", req.getParameter("option" + i))
					.set("question_id", question.getLast().getId())
					.set("is_correct", is_correct)
					.saveIt();
		}
	}


}
