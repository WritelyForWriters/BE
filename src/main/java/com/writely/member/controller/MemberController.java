package com.writely.member.controller;

import com.writely.common.domain.MemberSession;
import com.writely.member.response.MyProfileResponse;
import com.writely.member.service.MemberQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "회원")
public class MemberController {
    private final MemberQueryService memberQueryService;

    @GetMapping("/me/profile")
    public MyProfileResponse getMyProfile(MemberSession memberSession) {
        return memberQueryService.getMyProfile(memberSession);
    }

}
