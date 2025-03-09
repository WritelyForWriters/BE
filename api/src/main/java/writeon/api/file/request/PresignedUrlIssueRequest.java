package writeon.api.file.request;

import writeon.domain.file.enums.FileUploadType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PresignedUrlIssueRequest {
    @Schema(title = "업로드 타입", requiredMode = Schema.RequiredMode.REQUIRED, example = "idea-note, ...")
    private FileUploadType fileUploadType;

    @Schema(title = "원본파일 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "download.jpg")
    private String originalFileName;
}
