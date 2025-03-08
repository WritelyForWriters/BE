package writeon.api.terms.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import writeon.domain.terms.enums.TermsCode;

@AllArgsConstructor
@Getter
public class TermsAgreeRequest {
    @NotNull
    private TermsCode termsCd;

    @NotNull
    private Boolean isAgreed;
}
