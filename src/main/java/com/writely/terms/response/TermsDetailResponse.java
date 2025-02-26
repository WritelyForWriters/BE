package com.writely.terms.response;

import com.writely.terms.domain.enums.TermsCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writely.tables.records.TermsRecord;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TermsDetailResponse {
    @Schema(title = "약관 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;
    @Schema(title = "약관 코드", requiredMode = Schema.RequiredMode.REQUIRED)
    private TermsCode cd;
    @Schema(title = "약관 제목", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(title = "약관 내용", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
    @Schema(title = "약관 버전", requiredMode = Schema.RequiredMode.REQUIRED)
    private int version;
    @Schema(title = "약관 동의 필수 여부", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean isRequired;
    @Schema(title = "생성일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdAt;
    @Schema(title = "수정일", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updatedAt;

    public TermsDetailResponse(TermsRecord terms) {
        this.id = terms.getId();
        this.cd = TermsCode.fromCode(terms.getCd());
        this.title = terms.getTitle();
        this.content = terms.getContent();
        this.version = terms.getVersion();
        this.isRequired = terms.getIsRequired();
        this.createdAt = terms.getCreatedAt().toLocalDateTime();
        this.updatedAt = terms.getUpdatedAt().toLocalDateTime();
    }
}
