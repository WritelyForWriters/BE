package com.writely.member.response;


import com.writely.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyProfileResponse {
    @Schema(title = "이메일")
    private final String email;

    @Schema(title = "닉네임")
    private final String nickname;

    @Schema(title = "프로필 이미지 경로")
    private final String profileImage;

    public MyProfileResponse(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
    }
}
