package com.techtech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DogNotFoundException extends RuntimeException {
	
	//making a parameterized constructor so we can customise the message.
	public DogNotFoundException(String message) { 
		super(message);
	}

}
