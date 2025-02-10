package com.writely.terms.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import writely.tables.Terms;
import static org.jooq.impl.DSL.*;
import org.jooq.*;
import org.jooq.impl.*;
import writely.tables.records.TermsRecord;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermsDao {
    private final DSLContext dsl;
    private final Terms TERMS = writely.tables.Terms.TERMS;

    public List<com.writely.terms.domain.Terms> selectLatestTermsList() {
        Table<?> latestTerms = table(
                select(TERMS.CD, max(TERMS.VERSION).as("max_version"))
                        .from(TERMS)
                        .groupBy(TERMS.CD)
        ).as("latest_terms");

        return dsl.select() // 전체 필드 조회
                .from(TERMS)
                .join(latestTerms)
                    .on(TERMS.CD.eq(latestTerms.field(TERMS.CD)))
                    .and(TERMS.VERSION.eq(latestTerms.field("max_version", Integer.class)))
                .fetchInto(TermsRecord.class)
                .stream()
                .map(com.writely.terms.domain.Terms::new)
                .toList();
    }
}
