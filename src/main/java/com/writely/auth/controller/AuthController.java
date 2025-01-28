package com.writely.auth.controller;

import com.writely.auth.request.JoinRequest;
import com.writely.auth.request.JoinTokenRequest;
import com.writely.auth.request.LoginRequest;
import com.writely.auth.request.ReissueRequest;
import com.writely.auth.response.AuthTokenResponse;
import com.writely.auth.service.AuthCommandService;
import com.writely.auth.service.AuthQueryService;
import io.swagger.v3.oas.annotations.Operation;
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
    public AuthTokenResponse reissueToken(@Valid ReissueRequest request) {

        return authCommandService.reissueToken(request);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public AuthTokenResponse login(@RequestBody LoginRequest request) {

        return authCommandService.login(request);
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public void join(@RequestBody JoinRequest request) {

        authCommandService.join(request);
    }

    @Operation(summary = "회원가입 완료")
    @PostMapping("/join/complete")
    public AuthTokenResponse join(@RequestBody JoinTokenRequest request) {

        return authCommandService.completeJoin(request);
    }
}
