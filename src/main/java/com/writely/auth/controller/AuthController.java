package com.writely.auth.controller;

import com.writely.auth.request.*;
import com.writely.auth.response.AuthTokenResponse;
import com.writely.auth.response.CheckEmailResponse;
import com.writely.auth.service.AuthCommandService;
import com.writely.auth.service.AuthQueryService;
import com.writely.common.domain.MemberSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증")
public class AuthController {
    private final AuthQueryService authQueryService;
    private final AuthCommandService authCommandService;

    @Operation(summary = "토큰 재발급")
    @PostMapping("/token/reissue")
    public AuthTokenResponse reissueToken(@Valid @RequestBody ReissueRequest request) {

        return authCommandService.reissueToken(request);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public AuthTokenResponse login(@Valid @RequestBody LoginRequest request) {

        return authCommandService.login(request);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(@Parameter(hidden = true) MemberSession memberSession) {

        authCommandService.logout(memberSession.getMemberId());
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public void join(@Valid @RequestBody JoinRequest request) {

        authCommandService.join(request);
    }

    @Operation(summary = "회원가입 완료")
    @PostMapping("/join/complete")
    public AuthTokenResponse completeJoin(@Valid @RequestBody JoinCompletionRequest request) {

        return authCommandService.completeJoin(request);
    }

    @Operation(summary = "비밀번호 변경")
    @PostMapping("/change-password")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) {

        authCommandService.changePassword(request);
    }

    @Operation(summary = "비밀번호 변경 완료")
    @PostMapping("/change-password/complete")
    public void completeChangePassword(@Valid @RequestBody ChangePasswordCompletionRequest request) {

        authCommandService.completeChangePassword(request);
    }

    @Operation(summary = "이메일 중복 조회")
    @GetMapping("/check-email")
    public CheckEmailResponse checkEmail(@Valid CheckEmailRequest request) {

        return authQueryService.checkEmail(request);
    }
}
