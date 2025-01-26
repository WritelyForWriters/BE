package com.writely.auth.request;

import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    @Schema(title = "이메일")
    private String email;

    @Schema(title = "비밀번호")
    private String password;

    @Schema(title = "실명")
    private String realname;

    @Schema(title = "닉네임")
    private String nickname;

}
