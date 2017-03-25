package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class TemplatesControllerTest {

	@Test
	public void testProcessRequest() throws Exception {
		TemplatesController templateCtrl = new TemplatesController();

		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();

		Template template = new Template();
		template.setTemplateName("Default");
		template.setSubject("Greeting {FirstName}");
		template.setContent("Hi There {Company}");
		template.saveIt();

		templateCtrl.doGet(req, res);
		assertThat(res.getContentAsString(), containsString("\"Subject\":\"Greeting {FirstName}"));

	}
	
}
