package com.sangkyun.VideoSNS.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	// Static field to hold class version identifier
	private static final long serialVersionUID = 1L; // or any unique number

	public UserNotFoundException(String message) {
		super(message);
	}
}