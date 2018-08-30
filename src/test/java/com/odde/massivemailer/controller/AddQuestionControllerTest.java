package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
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
	public void addQuestiondTest() {
		long count = Question.count();
		request.setParameter("description", "What is Scrum?");
		controller.doPost(request, response);
		assertEquals(count + 1, (long) Question.count());
		Question question = (Question) Question.getLast();
		assertEquals("What is Scrum?",  question.getDescription());
	}

}
