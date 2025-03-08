package writeon.api.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import writely.tables.records.TermsRecord;
import writeon.api.common.exception.BaseException;
import writeon.api.terms.repository.TermsDao;
import writeon.api.terms.response.TermsDetailResponse;
import writeon.api.terms.response.TermsSummaryResponse;
import writeon.domain.terms.enums.TermsCode;
import writeon.domain.terms.enums.TermsException;

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

    public boolean isContainingAllRequiredTerms(List<TermsCode> termsCodeList) {

        return termsDao.isContainingAllRequiredTerms(termsCodeList);
    }

    public List<TermsSummaryResponse> getAllTermsList() {

        return termsDao.selectAllLatestTermsList()
                .stream()
                .map(TermsSummaryResponse::new)
                .toList();
    }
}
