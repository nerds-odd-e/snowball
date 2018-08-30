package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Options;
import com.odde.massivemailer.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

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
}
