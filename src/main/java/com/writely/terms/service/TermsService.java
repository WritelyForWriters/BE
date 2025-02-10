package com.writely.terms.service;

import com.writely.terms.repository.TermsDao;
import com.writely.terms.repository.TermsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermsService {
    private final TermsDao termsDao;
    private final TermsJpaRepository termsJpaRepository;

}
