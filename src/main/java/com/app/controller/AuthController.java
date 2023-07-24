package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payload.LoginDto;
import com.app.payload.RegisterDto;
import com.app.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(authService.login(loginDto));
	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
		return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
	}
}
