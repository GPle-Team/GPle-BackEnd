package com.gple.backend.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	@Setter
	Integer status;
	String message;

	public ExceptionResponse(String message){
		this.message = message;
	}
}
