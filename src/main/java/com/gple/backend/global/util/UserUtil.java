package com.gple.backend.global.util;

import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.exception.ExceptionEnum;
import com.gple.backend.global.exception.HttpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
            .orElseThrow(() -> new HttpException(ExceptionEnum.NOT_FOUND_USER));
    }
}
