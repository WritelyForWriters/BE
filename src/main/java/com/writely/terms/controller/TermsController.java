package com.writely.terms.controller;

import com.writely.terms.domain.enums.TermsCode;
import com.writely.terms.response.TermsDetailResponse;
import com.writely.terms.service.TermsQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

}
