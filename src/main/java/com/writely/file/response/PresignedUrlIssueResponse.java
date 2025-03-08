package com.writely.file.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PresignedUrlIssueResponse {
    private String presignedUrl;
}
