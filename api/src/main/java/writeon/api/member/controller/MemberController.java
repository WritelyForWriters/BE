package writeon.api.member.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import writeon.api.member.response.MyProfileResponse;
import writeon.api.member.service.MemberQueryService;
import writeon.domain.common.MemberSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "회원")
public class MemberController {
    private final MemberQueryService memberQueryService;

    @GetMapping("/me/profile")
    public MyProfileResponse getMyProfile() {
        return memberQueryService.getMyProfile();
    }

}
