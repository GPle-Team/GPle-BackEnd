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

	@ExceptionHandler(RuntimeException.class)
	ResponseEntity<ExceptionResponse> httpException(RuntimeException exception) throws JsonProcessingException {
		ExceptionResponse response = new ExceptionResponse(exception.getMessage());
		if(exception instanceof HttpException httpException){
			response = new ExceptionResponse(
				httpException.getStatus().value(), exception.getMessage()
			);
		}

		if(response.getStatus() == null) response.setStatus(500);

		log.error("{}", objectMapper.writeValueAsString(response));
		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
