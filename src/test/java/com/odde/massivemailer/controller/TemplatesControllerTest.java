package com.odde.massivemailer.controller;

import com.google.gson.Gson;
import com.odde.massivemailer.model.Template;
import com.odde.massivemailer.service.impl.SqliteTemplate;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TemplatesControllerTest {

	@Test
	public void testProcessRequest() throws Exception {
		SqliteTemplate templateService = mock(SqliteTemplate.class);
		TemplatesController templateCtrl = new TemplatesController(templateService);

		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();

		Template template = new Template();
		template.setId(1);
		template.setTemplateName("Default");
		template.setSubject("Greeting {FirstName}");
		template.setContent("Hi There {Company}");

		List<Template> expectedTemplates = new ArrayList<Template>();
		expectedTemplates.add(template);

		when(templateService.getTemplateList()).thenReturn(expectedTemplates);

		String convertedContactToJSON = new Gson().toJson(expectedTemplates);

		templateCtrl.doGet(req, res);
		assertEquals(convertedContactToJSON, res.getContentAsString());

	}
	
}
