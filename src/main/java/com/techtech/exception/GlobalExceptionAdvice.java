package com.techtech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
	
	//the line: throw new DogNotFoundException("Hey, it seems like this dog does not exist."); comes here.
	@ExceptionHandler(DogNotFoundException.class)
	public ResponseEntity<ErrorMessage> generateIt(DogNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(ex.getMessage()); //"Hey, it seems like this dog does not exist."
		errorMessage.setCode("C0292");
		return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	}
}
