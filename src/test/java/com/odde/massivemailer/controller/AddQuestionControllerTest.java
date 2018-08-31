package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Options;
import com.odde.massivemailer.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(TestWithDB.class)
public class AddQuestionControllerTest {
	private AddQuestionController controller;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@Before
	public void setUpMockService() {
		controller = new AddQuestionController();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}


	@Test
	public void addQuestionTest() {
		long count = Question.count();
		request.setParameter("description", "What is Scrum?");
		request.setParameter("advice", "some nice advice");
		request.setParameter("option1", "Scrum");
		request.setParameter("option2", "Soccer");
		controller.doPost(request, response);

		assertEquals(count + 1, (long) Question.count());
		Question question = Question.getLast();
		assertEquals("What is Scrum?",  question.getDescription());
		assertEquals("some nice advice",  question.getAdvice());
	}

	@Test
	public void addOptionTest() {
		long count = Options.count();
		request.setParameter("advice", "some nice advice");
		request.setParameter("description", "What is Scrum?");
		request.setParameter("option1", "Scrum");
		request.setParameter("option2", "Soccer");
		controller.doPost(request, response);

		assertEquals(count + 2, (long) Options.count());
		Options option1 = Options.getById(1);
		assertEquals("Scrum",  option1.getDescription());
		Options option2 = Options.getById(2);
		assertEquals("Soccer",  option2.getDescription());
	}

	@Test
	public void addCorrectAnswerTest() {
		request.setParameter("advice", "some nice advice");
		request.setParameter("description", "What is Scrum?");
		request.setParameter("option1", "Soccer");
		request.setParameter("option2", "Scrum");
		request.setParameter("is_correct", "1");
		controller.doPost(request, response);

		Options option2 = Options.getById(2);
		assertTrue(option2.getIsCorrect());
	}

	@Test
	public void add5OptionsTest() {
		long count = Options.count();
		String[] option_content = {
				"Soccer",
				"Scrum",
				"Football",
				"Baseball",
				"Kendo"
		};
		request.setParameter("advice", "some nice advice");
		request.setParameter("description", "What is Scrum?");
		setOptionContent(option_content);
		request.setParameter("is_correct", "1");
		controller.doPost(request, response);

		assertEquals(count + 5, (long) Options.count());
 		for (int i = 0; i < 5; i++) {
			Options option = Options.getById(i + 1);
			assertEquals(option_content[i],  option.getDescription());
		}
	}

	private void setOptionContent(String[] option_content) {
		for (int i = 0; i < option_content.length; i++) {
			request.setParameter("option" + (i+1), option_content[i]);
		}
	}
}
