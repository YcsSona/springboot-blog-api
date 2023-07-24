package com.app.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.Role;
import com.app.entity.User;
import com.app.exception.BlogAPIException;
import com.app.payload.LoginDto;
import com.app.payload.RegisterDto;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "User logged in successfully!";
	}

	@Override
	public String register(RegisterDto registerDto) {

		// Add check for username exist in database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is already exists");
		}

		// Add check for email exist in database
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email is already exists");
		}

		// Assigning USER role for new users
		Role userRole = roleRepository.findByName("ROLE_USER").get();

		Set<Role> roles = new HashSet<>();
		roles.add(userRole);

		User user = new User(registerDto.getName(), registerDto.getUsername(), registerDto.getEmail(),
				passwordEncoder.encode(registerDto.getPassword()), roles);

		userRepository.save(user);

		return "User registered successfully!";
	}

}
