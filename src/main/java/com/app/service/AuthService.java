package com.app.service;

import com.app.payload.LoginDto;

public interface AuthService {

	String login(LoginDto loginDto);
}
