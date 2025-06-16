package writeon.api.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class TokenReissueResponse {
    @Schema(title = "액세스 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3Mzc4Nzc1MDUsImV4cCI6MTc0NTY1MzUwNSwibWVtYmVySWQiOiJlZGMxNDJmNi1hODI4LTQ4MTQtODllOC1jZGM2NmU2ZTExMzMifQ.dEr2bidlj9lPe3ozoHFDc8e9IPxKaDdGiFQOl0HgiUM")
    private final String accessToken;

}
