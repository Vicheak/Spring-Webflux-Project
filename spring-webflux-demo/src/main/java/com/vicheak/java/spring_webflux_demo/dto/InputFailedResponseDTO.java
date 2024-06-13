package com.vicheak.java.spring_webflux_demo.dto;

import lombok.Data;

@Data
public class InputFailedResponseDTO {
	
	private int errorCode; 
	private String message; 
	private int input; 

}
