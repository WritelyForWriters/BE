package com.writely.terms.service;

import com.writely.common.exception.BaseException;
import com.writely.terms.domain.enums.TermsCode;
import com.writely.terms.domain.enums.TermsException;
import com.writely.terms.repository.TermsDao;
import com.writely.terms.response.TermsDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writely.tables.records.TermsRecord;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermsQueryService {
    private final TermsDao termsDao;

    public TermsDetailResponse getTermsByTermsCd(TermsCode termsCd) {
        TermsRecord terms = termsDao.selectLatestTermsByTermsCd(termsCd)
                .orElseThrow(() -> new BaseException(TermsException.TERMS_NOT_FOUND));

        return new TermsDetailResponse(terms);
    }

    public boolean isContainingRequiredTerms(List<TermsCode> termsCodeList) {

        return termsDao.isContainingRequiredTerms(termsCodeList);
    }

    public List<TermsDetailResponse> getAllTermsList() {

        return termsDao.selectAllLatestTermsList()
                .stream()
                .map(TermsDetailResponse::new)
                .toList();
    }
}
