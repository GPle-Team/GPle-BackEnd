package com.gple.backend.global.security.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomExceptionResponse {
	@Setter
	Integer status;

	@Setter
	String message;
}
