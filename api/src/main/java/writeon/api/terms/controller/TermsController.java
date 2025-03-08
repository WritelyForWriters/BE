package writeon.api.terms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import writeon.api.terms.response.TermsDetailResponse;
import writeon.api.terms.response.TermsSummaryResponse;
import writeon.api.terms.service.TermsQueryService;
import writeon.domain.terms.enums.TermsCode;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
@Tag(name = "약관")
@Slf4j
public class TermsController {
    private final TermsQueryService termsQueryService;

    @Operation(summary = "약관 상세 조회")
    @GetMapping("/{termsCd}")
    public TermsDetailResponse getTermsByTermsCd(@PathVariable TermsCode termsCd) {

        return termsQueryService.getTermsByTermsCd(termsCd);
    }

    @Operation(summary = "약관 목록 조회")
    @GetMapping("/")
    public List<TermsSummaryResponse> getTermsList() {

        return termsQueryService.getAllTermsList();
    }

}
