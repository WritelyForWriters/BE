package writeon.api.terms.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writely.tables.records.TermsRecord;
import writeon.domain.terms.enums.TermsCode;

import java.util.UUID;

@Getter
@Setter
public class TermsSummaryResponse {
    @Schema(title = "약관 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID id;
    @Schema(title = "약관 코드", requiredMode = Schema.RequiredMode.REQUIRED)
    private TermsCode cd;
    @Schema(title = "약관 제목", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    public TermsSummaryResponse(TermsRecord terms) {
        this.id = terms.getId();
        this.cd = TermsCode.fromCode(terms.getCd());
        this.title = terms.getTitle();
    }
}
