package writeon.api.member.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.domain.member.Member;

@Getter
@Setter
public class MyProfileResponse {
    @Schema(title = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String email;

    @Schema(title = "닉네임", requiredMode = Schema.RequiredMode.REQUIRED)
    private final String nickname;

    @Schema(title = "프로필 이미지 경로", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private final String profileImage;

    public MyProfileResponse(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage();
    }
}
