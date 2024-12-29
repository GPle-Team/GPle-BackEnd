package com.gple.backend.domain.user.service;

import com.gple.backend.domain.post.repository.PostRepository;
import com.gple.backend.domain.user.controller.dto.web.request.CreateUserProfileRequest;
import com.gple.backend.domain.user.controller.dto.web.response.GetUserResponse;
import com.gple.backend.domain.user.entity.User;
import com.gple.backend.domain.user.repository.UserRepository;
import com.gple.backend.global.thirdParty.aws.adapter.S3Adapter;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final S3Adapter s3Adapter;
    private final PostRepository postRepository;

    @Transactional
    public void createUserProfile(CreateUserProfileRequest request){
        User user = userUtil.getCurrentUser();

        if(request.getName() != null &&
            !user.getName().equals(request.getName())
        ){
            user.setName(request.getName());
        }

        if(request.getNumber() != null
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
        List<User> userList = userRepository.findAllByNameIsNotNullAndGradeIsNotNull();

        return userList.stream()
            .map(user -> GetUserResponse.builder()
                .name(user.getName())
                .id(user.getId())
                .grade(user.getGrade())
                .profileImage(user.getProfileImage())
                .build()).toList();
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

    @Transactional
    public List<GetUserResponse> popularityUser() {
        List<User> user = userRepository.findAll();
        List<User> popularUser = user.stream().sorted(Comparator.comparingInt(
                (User u) -> postRepository.findAllByUser(u).stream()
                        .mapToInt(p -> p.getEmoji().size())
                        .sum()
        ).reversed()).toList();

        return popularUser.subList(0, 3).stream().map(u -> GetUserResponse.builder()
                .id(u.getId())
                .name(u.getName())
                .grade(u.getGrade())
                .profileImage(u.getProfileImage())
                .build()).toList();
    }
}
