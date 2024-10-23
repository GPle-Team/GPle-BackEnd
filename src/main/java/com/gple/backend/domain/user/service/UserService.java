package com.gple.backend.domain.user.service;

import com.gple.backend.domain.user.controller.dto.web.request.CreateUserProfileRequest;
import com.gple.backend.domain.user.controller.dto.GetUserInfoResponse;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Transactional
    public void createUserProfile(CreateUserProfileRequest request){
        User user = userUtil.getCurrentUser();
        user.setStudentProfile(
            request.getUsername(),
            request.getStudentNumber()
        );

        userRepository.save(user);
    }

    public User getUserInfo(){
        return userUtil.getCurrentUser();
    }
}
