package com.writely.file.request;

import com.writely.file.enums.FileUploadType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PresignedUrlIssueRequest {
    @Schema(title = "업로드 타입", requiredMode = Schema.RequiredMode.REQUIRED, example = "idea-note, ...")
    private FileUploadType fileUploadType;

    @Schema(title = "메타 데이터", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "test")
    private Map<String, String> metadata;
}
