package com.syzegee.customer.events.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class GenericResponse<T> {
	private boolean success;
	private int status;
	private String message;
	private String description;
	private T data;

}
