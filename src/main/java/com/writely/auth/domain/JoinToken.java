package com.writely.auth.domain;

import com.writely.member.domain.Member;
import com.writely.member.domain.MemberPassword;
import com.writely.terms.domain.TermsAgreement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "joinToken", timeToLive = 60 * 60)
public class JoinToken extends BaseToken {
    private Member member;
    private MemberPassword memberPassword;
    private List<TermsAgreement> termsAgreementList;

    public JoinToken(String tokenString, Member member, MemberPassword memberPassword, List<TermsAgreement> termsAgreementList) {
        super(tokenString);
        this.member = member;
        this.memberPassword = memberPassword;
        this.termsAgreementList = termsAgreementList;
    }
}
