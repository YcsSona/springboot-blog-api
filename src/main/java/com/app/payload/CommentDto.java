package com.app.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private Long id;

	@NotEmpty(message = "Name should not be null or empty")
	private String name;

	@NotEmpty(message = "Name should not be null or empty")
	@Email
	private String email;

	@NotEmpty
	@Size(min = 10, message = "Body should have at least 10 characters")
	private String body;

}
