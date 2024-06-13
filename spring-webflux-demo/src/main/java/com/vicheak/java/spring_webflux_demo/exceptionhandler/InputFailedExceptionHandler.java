package com.vicheak.java.spring_webflux_demo.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vicheak.java.spring_webflux_demo.dto.InputFailedResponseDTO;
import com.vicheak.java.spring_webflux_demo.exception.InputFailedValidationException;

@RestControllerAdvice
public class InputFailedExceptionHandler {
	
	@ExceptionHandler(InputFailedValidationException.class)
	public ResponseEntity<InputFailedResponseDTO> inputFailedHandler(InputFailedValidationException ex){
		InputFailedResponseDTO response = new InputFailedResponseDTO(); 
		response.setErrorCode(ex.getErrorCode());
		response.setMessage(ex.getMessage());
		response.setInput(ex.getInput());
		
		return ResponseEntity.badRequest().body(response); 
	}

}
