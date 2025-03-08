package writeon.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writeon.api.auth.request.CheckEmailRequest;
import writeon.api.auth.response.CheckEmailResponse;
import writeon.domain.member.repository.MemberJpaRepository;

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
