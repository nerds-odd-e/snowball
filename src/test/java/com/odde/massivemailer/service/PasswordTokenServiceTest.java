package com.odde.massivemailer.service;

import org.junit.Assert;
import org.junit.Test;

public class PasswordTokenServiceTest {

	@Test
	public void test_create_token() {
		PasswordTokenService service = new PasswordTokenService();
		String token = service.createToken();

		Assert.assertNotNull(token);
	}
}
