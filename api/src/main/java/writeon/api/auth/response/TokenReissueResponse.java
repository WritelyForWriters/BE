package writeon.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import writeon.domain.auth.dto.AuthTokenDto;

import java.time.LocalDateTime;


@Getter
@Setter
public class TokenReissueResponse {
    @Schema(title = "액세스 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3Mzc4Nzc1MDUsImV4cCI6MTc0NTY1MzUwNSwibWVtYmVySWQiOiJlZGMxNDJmNi1hODI4LTQ4MTQtODllOC1jZGM2NmU2ZTExMzMifQ.dEr2bidlj9lPe3ozoHFDc8e9IPxKaDdGiFQOl0HgiUM")
    private final String accessToken;

    @Schema(title = "이전의 토큰 발급 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2025-04-05T14:41:10.626943")
    private final LocalDateTime previousTokenIssuedAt;

    public TokenReissueResponse(AuthTokenDto dto) {
        this.accessToken = dto.getAccessToken();
        this.previousTokenIssuedAt = dto.getPreviousTokenIssuedAt();
    }
}
