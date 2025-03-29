package writeon.api.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import writeon.api.auth.request.*;
import writeon.api.auth.response.AuthTokenResponse;
import writeon.api.auth.response.CheckEmailResponse;
import writeon.api.auth.service.AuthCommandService;
import writeon.api.auth.service.AuthQueryService;
import writeon.domain.common.MemberSession;

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
    public void logout() {

        authCommandService.logout();
    }

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public void join(@Valid @RequestBody JoinRequest request) {

        authCommandService.join(request);
    }

    @Operation(summary = "회원가입 완료")
    @PostMapping("/join/complete")
    public void completeJoin(@Valid @RequestBody JoinCompletionRequest request) {

        authCommandService.completeJoin(request);
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

    @Operation(summary = "닉네임 중복 조회")
    @GetMapping("/check-nickname")
    public CheckEmailResponse checkNickname(@Valid CheckNicknameRequest request) {

        return authQueryService.checkNickname(request);
    }
}
