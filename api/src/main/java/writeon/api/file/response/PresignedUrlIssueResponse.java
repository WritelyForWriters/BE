package writeon.api.file.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PresignedUrlIssueResponse {
    @Schema(title = "파일 업로드 url (presigned)", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://writely-bucket.s3.ap-northeast-2.amazonaws.com/idea-note/...")
    private String filePutUrl;

    @Schema(title = "파일 조회 url", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://writely-bucket.s3.ap-northeast-2.amazonaws.com/idea-note/...")
    private String fileGetUrl;
}
