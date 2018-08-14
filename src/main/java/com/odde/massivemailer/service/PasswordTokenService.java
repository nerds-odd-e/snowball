package com.odde.massivemailer.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordTokenService {

	public String createToken() {
		String ranStr = RandomStringUtils.random(1);
		return ranStr;
	}
}
