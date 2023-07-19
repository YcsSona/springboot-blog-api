package com.app.payload;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorDetails {

	private LocalDateTime timestamp;

	private String message;

	private String details;

	public ErrorDetails(String message, String details) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.details = details;
	}
}
