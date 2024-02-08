package com.sangkyun.VideoSNS.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sangkyun.VideoSNS.dto.AuthResponse;
import com.sangkyun.VideoSNS.dto.LoginRequest;
import com.sangkyun.VideoSNS.dto.SignUpRequest;
import com.sangkyun.VideoSNS.exception.DuplicatedUserInfoException;
import com.sangkyun.VideoSNS.model.User;
import com.sangkyun.VideoSNS.security.TokenProvider;
import com.sangkyun.VideoSNS.security.WebSecurityConfig;
import com.sangkyun.VideoSNS.security.oauth2.OAuth2Provider;
import com.sangkyun.VideoSNS.service.UserService;

//Define a controller class for authentication purposes, including login and signup operations.
@RequiredArgsConstructor // Lombok annotation to automatically generate a constructor with required arguments (final fields).
@RestController // Marks the class as a Spring MVC controller where every method returns a domain object instead of a view.
@RequestMapping("/auth") // Maps requests with the path "/auth" to methods in this controller.
public class AuthController {

	// Dependency injection of services and utilities used within the controller.
	private final UserService userService; // Service layer to manage user-related operations.
	private final PasswordEncoder passwordEncoder; // Utility for encoding passwords.
	private final AuthenticationManager authenticationManager; // Manages authentication processes.
	private final TokenProvider tokenProvider; // Utility class to generate and manage tokens.

	// Method to authenticate a user and return a token.
	@PostMapping("/authenticate") // Maps HTTP POST requests to the "/authenticate" path to this method.
	public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) { // Uses @Valid to enforce validation and @RequestBody to bind the incoming JSON to a LoginRequest object.
		String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
		return new AuthResponse(token); // Returns a response with the generated token.
	}

	// Method for user registration.
	@ResponseStatus(HttpStatus.CREATED) // Sets the response status to 201 (Created) on successful execution.
	@PostMapping("/signup") // Maps HTTP POST requests to the "/signup" path to this method.
	public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) { // Similar validation and binding for sign-up.
		// Checks for duplicate username or email and throws an exception if found.
		if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
			throw new DuplicatedUserInfoException(
					String.format("Username %s already been used", signUpRequest.getUsername()));
		}
		if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
			throw new DuplicatedUserInfoException(
					String.format("Email %s already been used", signUpRequest.getEmail()));
		}

		// Save the new user and generate a token.
		userService.saveUser(mapSignUpRequestToUser(signUpRequest));
		String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
		return new AuthResponse(token); // Returns a token in the response.
	}

	// Helper method to authenticate with username and password, then generate a token.
	private String authenticateAndGetToken(String username, String password) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		return tokenProvider.generate(authentication); // Generates a token based on the authentication object.
	}

	// Helper method to map sign-up request data to a User entity.
	private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
		User user = new User(); // Create a new User object.
		user.setUsername(signUpRequest.getUsername()); // Set username from request.
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // Encode and set password.
		user.setName(signUpRequest.getName()); // Set name from request.
		user.setEmail(signUpRequest.getEmail()); // Set email from request.
		user.setRole(WebSecurityConfig.USER); // Set the user role.
		user.setProvider(OAuth2Provider.LOCAL); // Set the provider to local indicating this is not an OAuth2 authentication.
		return user; // Return the populated User object.
	}
}
