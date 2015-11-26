package com.odde.massivemailer;

import com.google.gson.Gson;
import com.odde.massivemailer.controller.TemplatesController;
import com.odde.massivemailer.model.Template;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

public class TemplatesControllerTest {

	@Test
	public void testProcessRequest() throws Exception {
		MockHttpServletRequest req = new MockHttpServletRequest();
		MockHttpServletResponse res = new MockHttpServletResponse();

		Template expectedTemplate = new Template();
		expectedTemplate.Id = "1";
		expectedTemplate.TemplateName = "Default";
		expectedTemplate.Subject = "Greeting {FirstName}";
		expectedTemplate.Content = "Hi There {Company}";
		String convertedContactToJSON = new Gson().toJson(expectedTemplate);

		TemplatesController templateCtrl = new TemplatesController();
		templateCtrl.doGet(req, res);
		assertEquals(convertedContactToJSON, res.getContentAsString());

	}
	
}
