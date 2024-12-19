package com.gple.backend.domain.user.service;

import com.gple.backend.domain.user.controller.dto.web.request.CreateUserProfileRequest;
import com.gple.backend.domain.user.controller.dto.web.response.GetUserResponse;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.thirdParty.aws.adapter.S3Adapter;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final S3Adapter s3Adapter;
    private CreateUserProfileRequest request;

    @Transactional
    public void createUserProfile(CreateUserProfileRequest request){
        User user = userUtil.getCurrentUser();

        if(request.getName() != null &&
            !user.getUsername().equals(request.getName())
        ){
            user.setUsername(request.getName());
        }

        if(request.getNumber() != null &&
            !user.getStudentNumber().equals(request.getNumber())
        ){
            user.setStudentNumber(request.getNumber());
        }

        if(!request.getFile().isEmpty()){
            String profileImage = s3Adapter.uploadImage(request.getFile());
            user.setProfileImage(profileImage);
        }

        userRepository.save(user);
    }

    @Transactional
    public List<GetUserResponse> getUserList(){
        List<User> userList = userRepository.findAll();

        return userList.stream().map(user -> {
            long grade = 0L;
            if(user.getStudentNumber() != null){
                grade = Long.parseLong(user.getStudentNumber().substring(0, 1));
            }

            return GetUserResponse.builder()
                .name(user.getUsername())
                .id(user.getId())
                .grade(grade)
                .profileImage(user.getProfileImage())
                .build();
        }).toList();
    }
}
