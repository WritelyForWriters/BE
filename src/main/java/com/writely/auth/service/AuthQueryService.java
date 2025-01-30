package com.writely.auth.service;

import com.writely.auth.request.CheckEmailRequest;
import com.writely.auth.response.CheckEmailResponse;
import com.writely.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthQueryService {
    private final MemberJpaRepository memberJpaRepository;

    public CheckEmailResponse checkEmail(CheckEmailRequest request) {
        boolean exists = memberJpaRepository.findByEmail(request.getEmail()).isPresent();

        return new CheckEmailResponse(request.getEmail(), exists);
    }

}
