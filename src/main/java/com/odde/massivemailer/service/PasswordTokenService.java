package com.odde.massivemailer.service;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordTokenService implements TokenService {

	public String createToken() {
		String ranStr = RandomStringUtils.random(1);
		return ranStr;
	}
}
