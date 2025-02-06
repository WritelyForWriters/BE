package com.writely.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JoinCompletionRequest {
    @NotBlank
    @Schema(title = "회원가입 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3Mzc4Nzc1MDUsImV4cCI6MTc0NTY1MzUwNSwibWVtYmVySWQiOiJlZGMxNDJmNi1hODI4LTQ4MTQtODllOC1jZGM2NmU2ZTExMzMifQ.dEr2bidlj9lPe3ozoHFDc8e9IPxKaDdGiFQOl0HgiUM")
    private String joinToken;
}
