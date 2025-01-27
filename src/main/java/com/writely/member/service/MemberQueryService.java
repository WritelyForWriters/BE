package com.writely.member.service;

import com.writely.common.domain.MemberSession;
import com.writely.common.exception.BaseException;
import com.writely.member.domain.Member;
import com.writely.member.domain.enums.MemberException;
import com.writely.member.repository.MemberJpaRepository;
import com.writely.member.response.MyProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberJpaRepository memberJpaRepository;

    public MyProfileResponse getMyProfile(MemberSession memberSession) {
        Member member = memberJpaRepository.findById(memberSession.getMemberId())
                .orElseThrow(() -> new BaseException(MemberException.NOT_EXIST));

        return new MyProfileResponse(member);
    }
}
