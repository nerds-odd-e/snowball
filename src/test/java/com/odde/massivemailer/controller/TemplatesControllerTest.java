package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.Template;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(TestWithDB.class)
public class TemplatesControllerTest {

	private final TemplatesController templateCtrl = new TemplatesController();

	private final MockHttpServletRequest req = new MockHttpServletRequest();
	private final MockHttpServletResponse res = new MockHttpServletResponse();

	@Before
	public void setUp(){
		Template template = new Template();
		template.setTemplateName("Default");
		template.setSubject("Greeting {FirstName}");
		template.setContent("Hi There {Company}");
		template.saveIt();
	}

	@Test
	public void testProcessRequest() throws Exception {

		templateCtrl.doGet(req, res);
		assertThat(res.getContentAsString(), containsString("\"Subject\":\"Greeting {FirstName}"));

	}

	@Test
	public void testUpdateTemplate() throws Exception {
        Template template = Template.findFirst("templateName = ?", "Default");
        Object savedTemplateId = template.getId();
        req.setParameter("templateList", String.valueOf(savedTemplateId));
		req.setParameter("subject", "Hello Terry");
		req.setParameter("content", "Hello Terry, Cource details are below.");
		templateCtrl.doPost(req,res);
		Template savedTemplate = Template.findById(savedTemplateId);
        Assert.assertEquals("Hello Terry", savedTemplate.get("subject"));
	}
	
}
