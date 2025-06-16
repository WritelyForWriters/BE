package writeon.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.auth.helper.MemberHelper;
import writeon.api.common.exception.BaseException;
import writeon.api.member.response.MyProfileResponse;
import writeon.domain.common.MemberSession;
import writeon.domain.member.Member;
import writeon.domain.member.enums.MemberException;
import writeon.domain.member.repository.MemberJpaRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberJpaRepository memberJpaRepository;

    public MyProfileResponse getMyProfile() {
        MemberSession memberSession = MemberHelper.getMemberSession();

        Member member = memberJpaRepository.findById(memberSession.getMemberId())
                .orElseThrow(() -> new BaseException(MemberException.NOT_EXIST));

        return new MyProfileResponse(member);
    }
}
