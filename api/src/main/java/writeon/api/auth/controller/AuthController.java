package writeon.api.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import writeon.api.auth.helper.AuthCookieHelper;
import writeon.api.auth.request.*;
import writeon.api.auth.response.CheckEmailResponse;
import writeon.api.auth.response.TokenReissueResponse;
import writeon.api.auth.service.AuthCommandService;
import writeon.api.auth.service.AuthQueryService;
import writeon.api.common.response.BaseResponse;
import writeon.domain.auth.dto.AuthTokenDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증")
public class AuthController {
    private final AuthQueryService authQueryService;
    private final AuthCommandService authCommandService;
    private final AuthCookieHelper authCookieHelper;

    @Operation(summary = "토큰 재발급")
    @PostMapping("/token/reissue")
    public ResponseEntity<BaseResponse<TokenReissueResponse>> reissueToken(@CookieValue(value = "refreshToken", defaultValue="") String tokenString) {
        AuthTokenDto tokens = authCommandService.reissueToken(tokenString);
        TokenReissueResponse response = new TokenReissueResponse(tokens);

        String cookieString = authCookieHelper.createNewCookie(tokens.getRefreshToken());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookieString);

        return ResponseEntity.ok()
                .headers(headers)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenReissueResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthTokenDto tokens = authCommandService.login(request);
        TokenReissueResponse response = new TokenReissueResponse(tokens);

        String cookieString = authCookieHelper.createNewCookie(tokens.getRefreshToken());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookieString);

        return ResponseEntity.ok()
                .headers(headers)
                .body(BaseResponse.success(response));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<?>> logout() {
        authCommandService.logout();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authCookieHelper.createExpiredCookie());

        return ResponseEntity.ok()
                .headers(headers)
                .body(null);
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
