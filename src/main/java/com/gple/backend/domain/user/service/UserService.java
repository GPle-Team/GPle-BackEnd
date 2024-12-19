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
            !user.getName().equals(request.getName())
        ){
            user.setName(request.getName());
        }

        if(request.getNumber() != null &&
            !user.getNumber().equals(request.getNumber())
        ){
            user.setStudentNumber(
                Long.parseLong(request.getNumber().substring(0, 1)),
                Long.parseLong(request.getNumber().substring(1, 1)),
                Long.parseLong(request.getNumber().substring(2, 2))
            );
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
            if(user.getNumber() != null){
                grade = user.getGrade();
            }

            return GetUserResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .grade(grade)
                .profileImage(user.getProfileImage())
                .build();
        }).toList();
    }

    @Transactional
    public GetUserResponse getUserProfile(){
        User user = userUtil.getCurrentUser();
        return GetUserResponse.builder()
            .profileImage(user.getProfileImage())
            .grade(user.getGrade())
            .name(user.getName())
            .id(user.getId())
            .build();
    }
}
