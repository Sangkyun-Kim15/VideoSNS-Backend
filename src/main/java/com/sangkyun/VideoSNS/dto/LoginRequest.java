package com.sangkyun.VideoSNS.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//Annotate the class with @Data to generate all necessary boilerplate code for a POJO.
@Data
public class LoginRequest {

	@Schema(example = "user") // Provides an example value for OpenAPI documentation to illustrate expected input.
	@NotBlank // Ensures the username is not null and not just whitespace.
	private String username;

	@Schema(example = "user") // Similarly, provides an example for the password field in documentation.
	@NotBlank // Ensures the password is not null and not just whitespace.
	private String password;
}
