package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AddQuestionController extends AppController {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		HashMap<String, String> content = getParameterFromRequest(req, "description", "advice");
		Question question = Question.createSingleChoiceQuestion(content);

		int num_options = getNumOptions(req);
		for (int i = 1; i <= num_options; i++) {
			String parameter = req.getParameter("option" + i);
			question.addOption(i == 2, parameter);
		}
	}

	private int getNumOptions(HttpServletRequest req) {
		Enumeration question_content = req.getParameterNames();
		int num_options = 0;
		while(question_content.hasMoreElements()){
			String question_content_str = (String) question_content.nextElement();
			if(question_content_str.contains("option")) {
				num_options ++;
			}
		}
		return num_options;
	}
}
