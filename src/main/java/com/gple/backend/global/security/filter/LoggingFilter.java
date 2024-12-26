package com.gple.backend.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gple.backend.global.exception.HttpException;
import com.gple.backend.global.security.dto.RequestResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.gple.backend.global.security.dto.FilterExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestResponse requestResponse = RequestResponse.builder()
            .uri(request.getRequestURI())
            .method(request.getMethod())
            .authorization(request.getHeader("Authorization"))
            .build();

        log.info(objectMapper.writeValueAsString(requestResponse));

        try {
            filterChain.doFilter(request, response);
        } catch(HttpException e){
            response.setStatus(e.getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            FilterExceptionResponse exceptionResponse = new FilterExceptionResponse(e.getStatus().value(), e.getMessage());
            log.error("{}", objectMapper.writeValueAsString(response));

            objectMapper.writeValue(response.getWriter(), exceptionResponse);
        }
    }
}
