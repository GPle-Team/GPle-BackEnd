package com.gple.backend.domain.member.service;

import com.gple.backend.domain.member.controller.dto.GetUserInfoResponse;
import com.gple.backend.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberUtil memberUtil;

    public GetUserInfoResponse getUserInfo(){
        return new GetUserInfoResponse(memberUtil.getCurrentMember().getEmail());
    }
}
