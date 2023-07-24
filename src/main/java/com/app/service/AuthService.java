package com.app.service;

import com.app.payload.LoginDto;
import com.app.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}
