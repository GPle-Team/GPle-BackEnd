package com.gple.backend.global.security.details;

import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return new AuthDetails(
            userRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new HttpException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.")
            )
        );
    }
}
