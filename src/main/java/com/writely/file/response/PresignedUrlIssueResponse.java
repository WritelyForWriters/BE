package com.writely.file.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PresignedUrlIssueResponse {
    @Schema(title = "업로드 URL (presigned)", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://writely-bucket.s3.ap-northeast-2.amazonaws.com/idea-note/...")
    private String presignedUrl;

    @Schema(title = "업로드 될 파일 경로", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://writely-bucket.s3.ap-northeast-2.amazonaws.com/idea-note/...")
    private String uploadedFilePath;
}
