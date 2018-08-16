package com.odde.massivemailer.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PasswordTokenServiceTest {

	@Test
	public void test_create_token_longer_than_10() {
		PasswordTokenService service = new PasswordTokenService();
		String token = service.createToken();

		assertTrue(token.length() >= 10);
	}
	@Test
	public void test_randam_token() {
		PasswordTokenService passwordTokenService = new PasswordTokenService();
		String tokenOne = passwordTokenService.createToken();
		String tokenTwo = passwordTokenService.createToken();
		assertNotEquals(tokenOne, tokenTwo);
	}
}
