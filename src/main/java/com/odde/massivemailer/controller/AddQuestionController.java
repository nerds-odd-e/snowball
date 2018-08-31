package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class AddQuestionController extends AppController {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		HashMap<String, String> content = getParameterFromRequest(req, "description", "advice");
		Question question = Question.createSingleChoiceQuestion(content);

		for (int i = 1; i < 3; i++) {
			String parameter = req.getParameter("option" + i);
			question.addOption(i == 2, parameter);
		}
	}
}
