package com.writely.file.controller;

import com.writely.file.request.PresignedUrlIssueRequest;
import com.writely.file.response.PresignedUrlIssueResponse;
import com.writely.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
@Tag(name = "파일")
public class FileController {
    private final FileService fileService;

    @Operation(summary = "presigned url 발급")
    @PostMapping("/presigned-url")
    public PresignedUrlIssueResponse issuePresignedUrl(@RequestBody PresignedUrlIssueRequest params) {
        return fileService.issuePresignedUrl(params);
    }

}
