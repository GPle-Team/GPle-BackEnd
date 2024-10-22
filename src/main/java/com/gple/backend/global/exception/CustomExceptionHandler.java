package com.gple.backend.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gple.backend.global.security.dto.ExceptionResponse;
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

	@ExceptionHandler(HttpException.class)
	ResponseEntity<ExceptionResponse> httpException(HttpException exception) throws JsonProcessingException {
		ExceptionResponse response = new ExceptionResponse(
			exception.getStatusCode().value(), exception.getMessage()
		);

		log.error("{}", objectMapper.writeValueAsString(response));
		return ResponseEntity.status(exception.getStatusCode()).body(response);
	}
}
