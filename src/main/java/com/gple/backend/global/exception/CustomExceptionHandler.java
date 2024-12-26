package com.gple.backend.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gple.backend.global.security.dto.CustomExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
	private final ObjectMapper objectMapper;

	@ExceptionHandler(RuntimeException.class)
	ResponseEntity<CustomExceptionResponse> httpException(RuntimeException exception) throws JsonProcessingException {
		CustomExceptionResponse response = CustomExceptionResponse.builder()
			.message(exception.getMessage())
			.build();

		if(exception instanceof HttpException httpException){
			response.setStatus(httpException.getStatus().value());
			response.setMessage(httpException.getMessage());
		}

		if(response.getStatus() == null) response.setStatus(500);

		log.error("{}", objectMapper.writeValueAsString(response));
		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
