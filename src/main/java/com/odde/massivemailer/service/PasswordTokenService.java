package com.odde.massivemailer.service;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordTokenService implements TokenService {

	public String createToken() {
		return RandomStringUtils.random(1);
	}
}
