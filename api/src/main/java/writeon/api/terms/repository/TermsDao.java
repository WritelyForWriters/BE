package writeon.api.terms.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import writeon.domain.terms.enums.TermsCode;
import writeon.tables.records.TermsRecord;

import static org.jooq.impl.DSL.count;
import static writeon.tables.Terms.TERMS;

@Repository
@RequiredArgsConstructor
public class TermsDao {
    private final DSLContext dsl;

    public Optional<TermsRecord> selectLatestTermsByTermsCd(TermsCode termsCode) {
        return Optional.ofNullable(
                dsl.select()
                        .from(TERMS)
                        .where(TERMS.CD.eq(termsCode.getCode()))
                        .orderBy(TERMS.VERSION.desc())
                        .fetchAnyInto(TermsRecord.class)
        );
    }

    public List<TermsRecord> selectAllLatestTermsList() {
        return dsl.select()
                .distinctOn(TERMS.CD)
                .from(TERMS)
                .orderBy(TERMS.CD.asc(), TERMS.VERSION.desc())
                .fetchInto(TermsRecord.class);
    }

    public boolean isContainingAllRequiredTerms(List<TermsCode> termsCodeList) {
        int notAgreedTermsCount = dsl.selectCount()
                .from(TERMS)
                .where(
                        TERMS.IS_REQUIRED.eq(true)
                ).and(
                        TERMS.CD.notIn(termsCodeList.stream().map(TermsCode::getCode).toList())
                ).fetchOne(count());

        return notAgreedTermsCount == 0;
    }
}
