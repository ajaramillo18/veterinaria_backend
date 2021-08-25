package com.jama.api.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * @author ajara
 *
 */
@ControllerAdvice
public class RestExceptionHandler {/*

	
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(StudentNotFoundException exc) {
		
		// create a StudentErrorResponse
		
		ErrorResponse error = new ErrorResponse();
		Date date = new Date();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(date);
		
		// return ResponseEntity
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc) {
		
		// create a StudentErrorResponse
		ErrorResponse error = new ErrorResponse();
		Date date = new Date();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(date);
		
		// return ResponseEntity		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	*/
}
