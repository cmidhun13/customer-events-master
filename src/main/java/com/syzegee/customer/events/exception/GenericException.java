package com.syzegee.customer.events.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class GenericException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private boolean success;
	private int status;
	private String message;
	private String description;
	private String data;


}
