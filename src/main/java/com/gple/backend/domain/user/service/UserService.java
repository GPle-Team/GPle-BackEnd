package com.gple.backend.domain.user.service;

import com.gple.backend.domain.user.controller.dto.GetUserInfoResponse;
import com.gple.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserUtil userUtil;

    public GetUserInfoResponse getUserInfo(){
        return new GetUserInfoResponse(userUtil.getCurrentUser().getEmail());
    }
}
